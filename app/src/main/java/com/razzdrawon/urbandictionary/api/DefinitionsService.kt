package com.razzdrawon.urbandictionary.api

import com.razzdrawon.urbandictionary.model.UrbanDictionaryResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DefinitionsService {

    private val BASE_URL = "https://mashape-community-urban-dictionary.p.rapidapi.com/"
    private val api: DefinitionsApi

    init{
        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(DefinitionsApi::class.java)
    }

    fun getDefinitions(word: String): Single<UrbanDictionaryResponse> {
        return api.getDefinitions(word)
    }
}