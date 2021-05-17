package com.paninti.androidtestapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paninti.androidtestapp.HomeViewModel
import com.paninti.androidtestapp.databinding.ItemFilterBinding
import com.paninti.androidtestapp.repository.FilterType

class FilterAdapter(
    private val context: Context,
    private val filterList: ArrayList<FilterType>,
    val viewModel: HomeViewModel
) : RecyclerView.Adapter<FilterAdapter.FilterAdapterHolder>() {
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        p1: Int
    ): FilterAdapter.FilterAdapterHolder {
        val binding =
            ItemFilterBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return FilterAdapterHolder(binding)
    }

    override fun getItemCount(): Int = filterList.size

    override fun onBindViewHolder(holder: FilterAdapter.FilterAdapterHolder, position: Int) {
        holder.setIsRecyclable(false)
        with(holder) {
            with(filterList[position]) {
                binding.textFilterName.text = filterName

                binding.ivCheck.apply {
                    visibility = if (active) View.VISIBLE else View.GONE
                }

                holder.itemView.apply {
                    setOnClickListener {
                        resetSelectedFilter(position)
                        notifyDataSetChanged()
                        viewModel.updateSelectedFilter(filterList)
                    }
                }
            }
        }

    }

    fun resetAll() {
        for (element in filterList) {
            element.active = false
        }
    }

    private fun resetSelectedFilter(position: Int) {
        for (x in filterList.indices) {
            if (x == position) {
                filterList[x].active = !filterList[x].active
                continue
            }
            filterList[x].active = false
        }
    }

    inner class FilterAdapterHolder(val binding: ItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root)
}
