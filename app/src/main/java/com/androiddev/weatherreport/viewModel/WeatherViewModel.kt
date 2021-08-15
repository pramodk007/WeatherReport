package com.androiddev.weatherreport.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddev.weatherapi.Repository.WeatherRepository
import com.androiddev.weatherapi.data.Weather
import com.androiddev.weatherreport.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository):ViewModel() {

    private val weatherStateFlow: MutableStateFlow<ApiState> =  MutableStateFlow(ApiState.Empty)
    val _postStateFlow: StateFlow<ApiState> = weatherStateFlow

    fun getCityData(search: String) = viewModelScope.launch {
        weatherStateFlow.value = ApiState.Loading
        weatherRepository.getCityWeather(search)
            .catch { e->
                weatherStateFlow.value =ApiState.Failure(e)
            }
            .collect { data->
                weatherStateFlow.value = ApiState.Success(data)
            }
    }
}