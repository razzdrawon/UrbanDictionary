package com.razzdrawon.urbandictionary.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.razzdrawon.urbandictionary.api.DefinitionsService
import com.razzdrawon.urbandictionary.model.Definition
import com.razzdrawon.urbandictionary.model.UrbanDictionaryResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private val definitionsService = DefinitionsService()
    private val disposable = CompositeDisposable()

    val definitions = MutableLiveData<ArrayList<Definition>>()
    val definitionsError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        loading.value = true
        disposable.add(
            definitionsService.getDefinitions("hello")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<UrbanDictionaryResponse>() {
                    override fun onSuccess(res: UrbanDictionaryResponse) {
                        definitions.value = res.list
                        definitionsError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        definitionsError.value = true
                        loading.value = false
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
