package com.example.android.marsrealestate.overview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.databinding.ItemMarsProertyGridBinding
import com.example.android.marsrealestate.network.MarsProperty

class MarsPropertyItemViewHolder private constructor(
    private val binding: ItemMarsProertyGridBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var onItemClickListener: ((MarsProperty, MarsPropertyItemViewHolder) -> Unit)? = null

    fun bind(marsProperty: MarsProperty) {
        binding.apply {
            bMarsProperty = marsProperty
            constrainLayout.setOnClickListener {
                onItemClickListener?.let { it(marsProperty, this@MarsPropertyItemViewHolder) }
            }
        }
    }

    fun setOnItemClickListener(listener: (MarsProperty, MarsPropertyItemViewHolder) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        fun from(parent: ViewGroup): RecyclerView.ViewHolder =
            MarsPropertyItemViewHolder(ItemMarsProertyGridBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

}