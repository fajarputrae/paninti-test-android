package com.paninti.androidtestapp.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paninti.androidtestapp.HomeViewModel
import com.paninti.androidtestapp.adapter.FilterAdapter
import com.paninti.androidtestapp.databinding.LayoutBottomSheetFilterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class BottomSheetFilterFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModel<HomeViewModel>()

    private var _binding: LayoutBottomSheetFilterBinding? = null
    private val binding get() = _binding!!

    lateinit var filterName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutBottomSheetFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        getFilter()
        filterListObserver()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        findNavController().previousBackStackEntry?.savedStateHandle?.set("filterMode", "active")
        dismiss()
    }

    private fun setupView(){
        binding.ivClose.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set("filterMode", "active")
            dismiss()
        }
    }

    private fun getFilter(){
        val bundle = arguments
        if (bundle == null) {
            Timber.e("BottomSheetFilterFragment did not receive nation information")
            return
        }

        val args = BottomSheetFilterFragmentArgs.fromBundle(bundle)
        viewModel.updateSelectedFilter(args.filterList.data)
    }

    private fun filterListObserver() {
        viewModel.selectFilterSuccess.observe(viewLifecycleOwner, { it ->
            if (it is HomeViewModel.StateFilter.UpdateSelectedSuccess) {
                val filterList = it.data
                val filterListAdapter = FilterAdapter(requireContext(), filterList, viewModel)

                binding.rvFilter.apply {
                    filterListAdapter.notifyDataSetChanged()
                    adapter = filterListAdapter
                }
            }
        })
    }
}