package com.abc.app.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abc.app.databinding.ListItemBinding
import com.abc.app.data.model.Funds

class DataListAdapter(val isEmpty: (Boolean) -> Unit) : ListAdapter<Funds, RecyclerView.ViewHolder>(
    RecordsDiffCallback()
){
    private var data = listOf<Funds>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecordViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        (holder as RecordViewHolder).bind(data)
    }

    class RecordViewHolder(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Funds) {
            binding.apply {
                bindImageFromUrl(ivRecord, item.mainImageURL)
                tvTitle.text = item.title
                tvDesc.text = item.shortDescription
            }
        }
    }
}

class RecordsDiffCallback : DiffUtil.ItemCallback<Funds>() {

    override fun areItemsTheSame(oldItem: Funds, newItem: Funds): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Funds, newItem: Funds): Boolean {
        return oldItem == newItem
    }
}

