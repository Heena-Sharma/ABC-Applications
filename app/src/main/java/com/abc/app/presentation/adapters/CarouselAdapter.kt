package com.abc.app.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abc.app.databinding.ItemImageCarouselBinding
import com.abc.app.data.model.FundsOrg

class CarouselAdapter : ListAdapter<FundsOrg, RecyclerView.ViewHolder>(CarouselDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CarouselViewHolder(
            ItemImageCarouselBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        (holder as CarouselViewHolder).bind(data)
    }

    class CarouselViewHolder(
        private val binding: ItemImageCarouselBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FundsOrg) {
            binding.apply {
                bindImageFromUrl(ivImage, item.mainImageURL)
            }
        }
    }
}

class CarouselDiffCallback : DiffUtil.ItemCallback<FundsOrg>() {

    override fun areItemsTheSame(oldItem: FundsOrg, newItem: FundsOrg): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: FundsOrg, newItem: FundsOrg): Boolean {
        return oldItem == newItem
    }
}


