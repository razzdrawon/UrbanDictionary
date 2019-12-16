package com.razzdrawon.urbandictionary.api

import com.razzdrawon.urbandictionary.di.DaggerApiComponent
import com.razzdrawon.urbandictionary.model.UrbanDictionaryResponse
import io.reactivex.Single
import javax.inject.Inject

class DefinitionsService {

    @Inject
    lateinit var api: DefinitionsApi

    init{
        DaggerApiComponent.create().inject(this)
    }

    fun getDefinitions(word: String): Single<UrbanDictionaryResponse> {
        return api.getDefinitions(word)
    }
}