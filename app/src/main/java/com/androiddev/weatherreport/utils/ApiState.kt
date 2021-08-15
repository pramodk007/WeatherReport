package com.androiddev.weatherreport.utils

import com.androiddev.weatherapi.data.Weather

sealed class ApiState {
    object Loading : ApiState()
    class Failure(val msg:Throwable) : ApiState()
    class Success(val data:Weather) :ApiState()
    object Empty : ApiState()
}