package com.razzdrawon.urbandictionary.di

import com.razzdrawon.urbandictionary.api.DefinitionsApi
import com.razzdrawon.urbandictionary.api.DefinitionsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    private val BASE_URL = "https://mashape-community-urban-dictionary.p.rapidapi.com/"

    @Provides
    fun provideDefinitionsApi(): DefinitionsApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(DefinitionsApi::class.java)
    }

    @Provides
    fun provideDefinitionsService(): DefinitionsService {
        return DefinitionsService()
    }
}