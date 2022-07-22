package com.example.android.marsrealestate.overview.adapter

import com.example.android.marsrealestate.network.MarsProperty

sealed class OverviewData {

    abstract val id: Int

    data class Header(val header:String) : OverviewData() {
        override val id: Int get() = 0
    }
    data class Item(val marsProperty: MarsProperty) : OverviewData() {
        override val id: Int get() = 1
    }

}
