package com.example.android.marsrealestate.overview.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException

/**
 * # Adapter
 */
class MarsPropertyAdapter : ListAdapter<OverviewData, RecyclerView.ViewHolder>(MarsPropertyComparer()) {

    /**
     * adapter coroutine scope
     */
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    /**
     * return view holder depending on viewType id
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        0 -> MarsPropertyHeaderViewHolder.from(parent)
        1 -> MarsPropertyItemViewHolder.from(parent)
        else -> throw IllegalArgumentException("Unknown View Holder")
    }

    /**
     * return the corresponding view type depending on item's type
     */
    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is OverviewData.Header -> 0
        is OverviewData.Item -> 1
    }

    /**
     * bind exact data to it's view holder
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MarsPropertyHeaderViewHolder -> {
                val item = getItem(position) as OverviewData.Header
                holder.bind(item.header)
            }
            is MarsPropertyItemViewHolder -> {
                val item = getItem(position) as OverviewData.Item
                holder.bind(item.marsProperty)
                holder.setOnItemClickListener(onItemClickListener)
            }
        }
    }

    /**
     * add header then list
     */
    fun addHeaderAndSubmitList(list: List<MarsProperty>?) {
        adapterScope.launch {
            val header = listOf(OverviewData.Header("change this text when you want"))
            val items = when(list) {
                null -> header
                else -> header + list.map { OverviewData.Item(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    /**
     * onclick Listener from all types
     */
    private lateinit var onItemClickListener: (MarsProperty, MarsPropertyItemViewHolder) -> Unit

    fun setOnItemClickListener(listener: (MarsProperty, RecyclerView.ViewHolder) -> Unit) {
        onItemClickListener = listener
    }
}


