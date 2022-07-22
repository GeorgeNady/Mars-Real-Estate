package com.example.android.marsrealestate.overview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.databinding.ItemMarsProertyHeaderBinding

class MarsPropertyHeaderViewHolder private constructor(private val binding: ItemMarsProertyHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(header: String) {
        binding.apply {
            bHeader = header
        }
    }

    companion object {
        fun from(parent: ViewGroup): RecyclerView.ViewHolder =
            MarsPropertyHeaderViewHolder(ItemMarsProertyHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
    }

}