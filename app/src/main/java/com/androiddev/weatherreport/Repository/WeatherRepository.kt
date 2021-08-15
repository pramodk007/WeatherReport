package com.androiddev.weatherapi.Repository

import com.androiddev.weatherapi.data.Weather
import com.androiddev.weatherapi.network.WeatherServiceApiImplementation
import com.androiddev.weatherapi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherServiceApiImplementation: WeatherServiceApiImplementation) {

    fun getCityWeather(city:String):Flow<Weather> = flow {
        emit(weatherServiceApiImplementation.getCityWeather(Constants.KEY,city))
    }.flowOn(Dispatchers.IO)
}