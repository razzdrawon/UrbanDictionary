package com.razzdrawon.urbandictionary.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.razzdrawon.urbandictionary.api.DefinitionsService
import com.razzdrawon.urbandictionary.di.DaggerApiComponent
import com.razzdrawon.urbandictionary.model.Definition
import com.razzdrawon.urbandictionary.model.UrbanDictionaryResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel : ViewModel() {

    @Inject
    lateinit var definitionsService: DefinitionsService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposable = CompositeDisposable()

    val definitions = MutableLiveData<ArrayList<Definition>>()
    val definitionsError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(word: String) {
        fetchCountries(word)
    }

    private fun fetchCountries(word: String) {
        loading.value = true
        disposable.add(
            definitionsService.getDefinitions(word)
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
