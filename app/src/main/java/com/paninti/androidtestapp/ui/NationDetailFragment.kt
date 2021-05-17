package com.paninti.androidtestapp.ui

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.paninti.androidtestapp.databinding.FragmentNationDetailBinding
import com.paninti.lib.base.fragment.BaseFragment
import timber.log.Timber

class NationDetailFragment : BaseFragment() {

    private var _binding: FragmentNationDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNationDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        binding.ivBack.setOnClickListener { findNavController().popBackStack() }

        val bundle = arguments
        if (bundle == null) {
            Timber.e("NationDetailFragment did not receive nation information")
            return
        }

        val args = NationDetailFragmentArgs.fromBundle(bundle)

        GlideToVectorYou.justLoadImage(
            context as Activity,
            Uri.parse(args.nationInformation.flag),
            binding.imgNationFlag
        )
        binding.textCountryName.text = args.nationInformation.name
        binding.textRegionName.text = args.nationInformation.region
        binding.textLng.text = args.nationInformation.latlng?.get(1).toString()
        binding.textLat.text = args.nationInformation.latlng?.get(0).toString()


        val languageList = arrayListOf<String>()
        args.nationInformation.languages?.forEach {
            languageList.add(it.name)
        }
        for (i in 0 until languageList.size) {
            binding.textLanguage.append(languageList[i] + "\n")
        }
    }

}