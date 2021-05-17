package com.paninti.androidtestapp.adapter

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.paninti.androidtestapp.R
import com.paninti.androidtestapp.databinding.ItemNationBinding
import com.paninti.androidtestapp.repository.AllResponse

class NationAdapter (private val context: Context,
                     private val nationList: List<AllResponse>
) : RecyclerView.Adapter<NationAdapter.NationAdapterHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): NationAdapter.NationAdapterHolder {
        val binding = ItemNationBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return NationAdapterHolder(binding)
    }

    override fun getItemCount(): Int = nationList.size

    override fun onBindViewHolder(holder: NationAdapter.NationAdapterHolder, position: Int) {
        holder.setIsRecyclable(false)
        with(holder){
            with(nationList[position]){

                GlideToVectorYou.justLoadImage(context as Activity, Uri.parse(flag), binding.ivNationPict)

                binding.textAkun.text = name

                holder.itemView.apply {
                    setOnClickListener {
                        val bundle = bundleOf("nationInformation" to nationList[position])
                        itemView.findNavController().navigate(R.id.action_homeFragment_to_nationDetailFragment, bundle)
                    }
                }
            }
        }

    }

    inner class NationAdapterHolder(val binding: ItemNationBinding) :RecyclerView.ViewHolder(binding.root)
}