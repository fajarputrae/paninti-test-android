package com.paninti.androidtestapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paninti.androidtestapp.R
import com.paninti.androidtestapp.databinding.ItemContinentBinding
import com.paninti.androidtestapp.repository.RegionedResponse

class RegionAdapter(
    private val context: Context,
    private val regionList: ArrayList<RegionedResponse>
) : RecyclerView.Adapter<RegionAdapter.RegionAdapterHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): RegionAdapterHolder {
        val binding = ItemContinentBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return RegionAdapterHolder(binding)
    }

    override fun getItemCount(): Int = regionList.size

    override fun onBindViewHolder(holder: RegionAdapterHolder, position: Int) {
        holder.setIsRecyclable(false)
        with(holder){
            with(regionList[position]){
                binding.tvContinentName.text = regionName
                binding.expandedView.visibility = if (this.expand) View.VISIBLE else View.GONE
                binding.ivIndicator.setImageResource(if (this.expand) R.drawable.ic_up else R.drawable.ic_down)
                binding.cardLayout.setOnClickListener {
                    this.expand = !this.expand
                    notifyDataSetChanged()
                }
                val mAdapter = NationAdapter(context, data)
                binding.rvNations.apply {
                    adapter = mAdapter
                }

            }
        }

    }

    inner class RegionAdapterHolder(val binding: ItemContinentBinding) :RecyclerView.ViewHolder(binding.root)
}