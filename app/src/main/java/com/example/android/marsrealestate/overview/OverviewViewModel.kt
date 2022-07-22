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

import androidx.lifecycle.*
import com.example.android.marsrealestate.network.MarsNetworkConfig.retrofitService
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    enum class State {
        LOADING, DONE, ERROR
    }

    // The internal MutableLiveData
    private val _state = MutableLiveData<State>()
    private val _response = MutableLiveData<List<MarsProperty>>()
    private val _spans = MutableLiveData<Int>()
    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()

    // The external immutable LiveData
    // val state: LiveData<State> get() = _state
    val response: LiveData<List<MarsProperty>> get() = _response
    val spans: LiveData<Int> get() = _spans
    val navigateToSelectedProperty: LiveData<MarsProperty> get() = _navigateToSelectedProperty
    val showProgressBarEvent = Transformations.map(_state) { it == State.LOADING }
    val showRecyclerViewEvent = Transformations.map(_state) { it == State.DONE }
    val showErrorMessageEvent = Transformations.map(_state) { it == State.ERROR }
    val listForResponse = Transformations.map(_response) { pl -> pl.map { p -> p.imgSrc } }

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties()
        _spans.value = 3
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getMarsRealEstateProperties() {
        _state.value = State.LOADING
        viewModelScope.launch {
            try {
                val result = retrofitService.getProperties()
                _state.value = State.DONE
                _response.value = result
            } catch (t: Throwable) {
                _state.value = State.ERROR
            }
        }
    }

    fun setGridLayoutSpans(spans: Int) {
        _spans.value = spans
    }
}