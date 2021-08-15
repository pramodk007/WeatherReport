package com.androiddev.weatherapi.di

import com.androiddev.weatherapi.network.WeatherServiceApi
import com.androiddev.weatherapi.network.WeatherServiceApiImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideUrl():String = "http://api.weatherapi.com/v1/"

    @Singleton
    @Provides
    fun provideRetrofit(url:String) : Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideWeatherService(retrofit: Retrofit): WeatherServiceApi = retrofit.create(WeatherServiceApi::class.java)

    @Singleton
    @Provides
    fun provideSongServiceImplementation(weatherServiceApi: WeatherServiceApi) = WeatherServiceApiImplementation(weatherServiceApi)

}