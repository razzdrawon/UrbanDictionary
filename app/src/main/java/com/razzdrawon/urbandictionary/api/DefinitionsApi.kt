package com.razzdrawon.urbandictionary.api

import com.razzdrawon.urbandictionary.model.UrbanDictionaryResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DefinitionsApi {

    @Headers("x-rapidapi-host: mashape-community-urban-dictionary.p.rapidapi.com",
        "x-rapidapi-key: beef6e6d56mshfc5ce023619c573p1b6b7bjsnfbdbf1dfb2a7")
    @GET("/define")
    fun getDefinitions(@Query("term") query: String): Single<UrbanDictionaryResponse>
}