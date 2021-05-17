package com.paninti.androidtestapp.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.paninti.androidtestapp.HomeViewModel
import com.paninti.androidtestapp.R
import com.paninti.androidtestapp.adapter.NationAdapter
import com.paninti.androidtestapp.adapter.RegionAdapter
import com.paninti.androidtestapp.databinding.FragmentHomeBinding
import com.paninti.androidtestapp.repository.AllResponse
import com.paninti.androidtestapp.repository.FilterList
import com.paninti.androidtestapp.repository.RegionedResponse
import com.paninti.lib.base.extension.afterTextChanged
import com.paninti.lib.base.extension.gone
import com.paninti.lib.base.extension.hideKeyboard
import com.paninti.lib.base.extension.visible
import com.paninti.lib.base.fragment.BaseFragment
import com.paninti.lib.base.viewmodel.Message
import org.jetbrains.anko.support.v4.onRefresh
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private val viewModel by viewModel<HomeViewModel>()
    private var searchList = arrayListOf<AllResponse>()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var nav: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = Navigation.findNavController(view)

        setupView()
        getAllNations()
        viewModel.setFilter()
        filterListObserver()
        nationsListObserve()

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("filterMode")
            ?.observe(viewLifecycleOwner) {
                if (it == "active") {
                    filterListObserver()
                }
            }
    }

    private fun setupView() {
        binding.srlMain.apply {
            setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorPrimary
                )
            )
            setColorSchemeColors(Color.WHITE)
            onRefresh { viewModel.getAllNations() }
        }

        binding.etSearch.apply {

            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.textFilter.gone()
                    binding.srlMain.gone()
                    binding.ivCloseSearch.visible()
                    binding.flSearchNation.visible()
                }
            }

            afterTextChanged {
                val prefix = binding.etSearch.text.toString()
                val keyword = searchList.filter { it.name!!.contains(prefix, true) }

                if (prefix.isNotEmpty()) {
                    if (keyword.isNotEmpty()) {
                        binding.textTitleEmptySearch.gone()
                        val nationSearchAdapter = NationAdapter(requireContext(), keyword)

                        binding.rvSearchNation.apply {
                            visible()
                            layoutManager = LinearLayoutManager(context)
                            nationSearchAdapter.notifyDataSetChanged()
                            adapter = nationSearchAdapter
                        }
                    } else {
                        binding.rvSearchNation.gone()
                        binding.textTitleEmptySearch.visible()
                    }
                } else {
                    binding.rvSearchNation.gone()
                    binding.textTitleEmptySearch.visible()
                }

            }
        }

        binding.ivCloseSearch.apply {
            setOnClickListener {
                gone()
                binding.flSearchNation.gone()

                binding.etSearch.apply {
                    clearFocus()
                    hideKeyboard()
                    text?.clear()
                }

                binding.textFilter.visible()
                binding.srlMain.visible()
            }
        }

    }

    private fun getAllNations() {
        binding.srlMain.apply {
            visible()
            isRefreshing = true
        }
        viewModel.getAllNations()
    }

    private fun nationsListObserve() {
        viewModel.getAllNationsSuccess.observe(viewLifecycleOwner, { it ->
            if (it is HomeViewModel.StateAllNations.AllNationsSuccess) {

                searchList = it.data

                val africaList = arrayListOf<AllResponse>()
                val americasList = arrayListOf<AllResponse>()
                val asiaList = arrayListOf<AllResponse>()
                val europeList = arrayListOf<AllResponse>()
                val oceaniaList = arrayListOf<AllResponse>()

                it.data.forEach {
                    when (it.region) {
                        "Africa" -> africaList.add(it)
                        "Americas" -> americasList.add(it)
                        "Asia" -> asiaList.add(it)
                        "Europe" -> europeList.add(it)
                        "Oceania" -> oceaniaList.add(it)
                    }
                }

                val africaRegionList = RegionedResponse("Africa", africaList, false)
                val americasRegionList = RegionedResponse("Americas", americasList, false)
                val asiaRegionList = RegionedResponse("Asia", asiaList, false)
                val europeRegionList = RegionedResponse("Europe", europeList, false)
                val oceaniaRegionList = RegionedResponse("Oceania", oceaniaList, false)

                val regions = arrayListOf<RegionedResponse>()
                regions.add(africaRegionList)
                regions.add(americasRegionList)
                regions.add(asiaRegionList)
                regions.add(europeRegionList)
                regions.add(oceaniaRegionList)

                val consultationListAdapter = RegionAdapter(requireContext(), regions)

                if (it.data.isNotEmpty()) {
                    binding.rvContinents.apply {
                        visible()
                        consultationListAdapter.notifyDataSetChanged()
                        adapter = consultationListAdapter
                    }
                }

                binding.srlMain.apply {
                    isRefreshing = false
                }

            } else {
                binding.srlMain.apply {
                    isRefreshing = false
                }
                showMessage(Message.Toast("Gagal mengambil data!"))
            }
        })
    }

    private fun filterListObserver() {
        viewModel.selectFilterSuccess.observe(viewLifecycleOwner, { it ->
            if (it is HomeViewModel.StateFilter.UpdateSelectedSuccess) {
                val filterList = FilterList(it.data)

                for (item in it.data) {
                    if (item.active) {
                        binding.textFilter.setBackgroundResource(R.drawable.bg_rounded_fill)
                        binding.textFilter.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorWhite))

                        binding.srlMain.gone()
                        binding.textTitleEmptySearch.gone()
                        binding.flSearchNation.visible()

                        when(item.filterName){
                            "Largest Population" -> {
                                val filterKeyword = searchList.sortedByDescending { it.population }
                                val nationSearchAdapter = NationAdapter(requireContext(), filterKeyword)
                                binding.rvSearchNation.apply {
                                    visible()
                                    layoutManager = LinearLayoutManager(context)
                                    nationSearchAdapter.notifyDataSetChanged()
                                    adapter = nationSearchAdapter
                                }
                            }
                            "Smallest Population" -> {
                                val filterKeyword = searchList.sortedBy { it.population }
                                val nationSearchAdapter = NationAdapter(requireContext(), filterKeyword)
                                binding.rvSearchNation.apply {
                                    visible()
                                    layoutManager = LinearLayoutManager(context)
                                    nationSearchAdapter.notifyDataSetChanged()
                                    adapter = nationSearchAdapter
                                }
                            }
                            "Africa" -> {
                                val filterKeyword = arrayListOf<AllResponse>()

                                searchList.forEach {
                                    if(it.region.equals("Africa", true)){
                                        filterKeyword.add(it)
                                    }
                                }

                                val nationSearchAdapter = NationAdapter(requireContext(), filterKeyword)
                                binding.rvSearchNation.apply {
                                    visible()
                                    layoutManager = LinearLayoutManager(context)
                                    nationSearchAdapter.notifyDataSetChanged()
                                    adapter = nationSearchAdapter
                                }
                            }
                            "Americas" -> {
                                val filterKeyword = arrayListOf<AllResponse>()

                                searchList.forEach {
                                    if(it.region.equals("Americas", true)){
                                        filterKeyword.add(it)
                                    }
                                }

                                val nationSearchAdapter = NationAdapter(requireContext(), filterKeyword)
                                binding.rvSearchNation.apply {
                                    visible()
                                    layoutManager = LinearLayoutManager(context)
                                    nationSearchAdapter.notifyDataSetChanged()
                                    adapter = nationSearchAdapter
                                }
                            }
                            "Asia" -> {
                                val filterKeyword = arrayListOf<AllResponse>()

                                searchList.forEach {
                                    if(it.region.equals("Asia", true)){
                                        filterKeyword.add(it)
                                    }
                                }

                                val nationSearchAdapter = NationAdapter(requireContext(), filterKeyword)
                                binding.rvSearchNation.apply {
                                    visible()
                                    layoutManager = LinearLayoutManager(context)
                                    nationSearchAdapter.notifyDataSetChanged()
                                    adapter = nationSearchAdapter
                                }
                            }
                            "Europe" -> {
                                val filterKeyword = arrayListOf<AllResponse>()

                                searchList.forEach {
                                    if(it.region.equals("Europe", true)){
                                        filterKeyword.add(it)
                                    }
                                }

                                val nationSearchAdapter = NationAdapter(requireContext(), filterKeyword)
                                binding.rvSearchNation.apply {
                                    visible()
                                    layoutManager = LinearLayoutManager(context)
                                    nationSearchAdapter.notifyDataSetChanged()
                                    adapter = nationSearchAdapter
                                }
                            }
                            "Oceania" -> {
                                val filterKeyword = arrayListOf<AllResponse>()

                                searchList.forEach {
                                    if(it.region.equals("Oceania", true)){
                                        filterKeyword.add(it)
                                    }
                                }

                                val nationSearchAdapter = NationAdapter(requireContext(), filterKeyword)
                                binding.rvSearchNation.apply {
                                    visible()
                                    layoutManager = LinearLayoutManager(context)
                                    nationSearchAdapter.notifyDataSetChanged()
                                    adapter = nationSearchAdapter
                                }
                            }
                        }
                        break
                    }
                    else{
                        binding.flSearchNation.gone()
                        binding.srlMain.visible()

                        binding.textFilter.setBackgroundResource(0)
                        binding.textFilter.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                    }
                }

                binding.textFilter.apply {
                    setOnClickListener {
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToBottomSheet(filterList)
                        NavHostFragment.findNavController(this@HomeFragment).navigate(action)
                    }
                }
            }
        })
    }

}