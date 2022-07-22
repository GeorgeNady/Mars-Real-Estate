package com.example.android.marsrealestate.overview.adapter

import androidx.recyclerview.widget.DiffUtil

/**
 * # Data Comparer
 */
class MarsPropertyComparer : DiffUtil.ItemCallback<OverviewData>() {
    override fun areItemsTheSame(oldItem: OverviewData, newItem: OverviewData) =
        oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: OverviewData, newItem: OverviewData) =
        oldItem == newItem
}