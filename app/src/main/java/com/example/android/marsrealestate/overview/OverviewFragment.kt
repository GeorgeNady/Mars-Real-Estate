/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.R
import com.example.android.marsrealestate.databinding.FragmentOverviewBinding
import com.example.android.marsrealestate.overview.adapter.MarsPropertyAdapter

/**
 * This fragment shows the the status of the Mars real-estate web services transaction.
 */
class OverviewFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this)[OverviewViewModel::class.java]
    }
    private val binding by lazy { FragmentOverviewBinding.inflate(layoutInflater) }
    private val adapter by lazy { MarsPropertyAdapter() }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding.apply {

            // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
            lifecycleOwner = this@OverviewFragment

            // Giving the binding access to the OverviewViewModel
            overviewViewModel = viewModel
            bAdapter = adapter

            // rvPhotosGrid.doubleTouchHandler()
            viewModel.observables()

            adapter.setOnItemClickListener { marsProperty, _ ->
                findNavController().navigate(OverviewFragmentDirections.actionShowDetail(marsProperty))
            }

            setHasOptionsMenu(true)
        }
        return binding.root
    }

    /**
     * observables
     */
    private fun OverviewViewModel.observables() {
        spans.observe(viewLifecycleOwner, Observer {
            binding.apply {
                bLayoutManager = rvPhotosGrid.setAdapterWithHeader(it)
            }
        })
        response.observe(viewLifecycleOwner, Observer { marsProperties ->
            Log.d(TAG, "marsProperties: $marsProperties")
            adapter.addHeaderAndSubmitList(marsProperties)
        })
    }

    /**
     * UI handle Grid view
     */
    private fun RecyclerView.setAdapterWithHeader(spans: Int) : RecyclerView.LayoutManager {
        val manager = GridLayoutManager(context, spans)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = when (position) {
                0 -> spans
                else -> 1
            }
        }
        return manager
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun View.doubleTouchHandler() {
        setOnTouchListener { _, event ->
            val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent?): Boolean {
                    Log.d(TAG, "onDoubleTap: ")
                    return super.onDoubleTap(e)
                }
            })

            Log.d(TAG, "Raw event: ${event.action}, ( ${event.rawX}, ${event.rawY})")
            gestureDetector.onTouchEvent(event)
            true
        }
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private companion object {
        val TAG = this::class.simpleName
    }

}
