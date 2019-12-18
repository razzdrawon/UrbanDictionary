package com.razzdrawon.urbandictionary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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

    val contentState: LiveData<List<Definition>> = Transformations.map(definitions) { response ->
        when (selectedSort) {
            SortOptions.THUMBS_UP -> response.sortedByDescending { it.thumbsUp }
            SortOptions.THUMBS_DOWN -> response.sortedByDescending { it.thumbsDown }
            SortOptions.DEFAULT -> response
        }
    }

    internal var selectedSort: SortOptions = SortOptions.DEFAULT
        private set

    fun refresh(word: String) {
        fetchCountries(word)
    }

    private fun fetchCountries(word: String) {
        loading.value = true
        disposable.add(
            definitionsService.getDefinitions(word)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<UrbanDictionaryResponse>() {
                    override fun onSuccess(res: UrbanDictionaryResponse) {
                        definitions.value = res.list
                        definitionsError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        definitionsError.value = true
                        loading.value = false
                    }
                }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun sortWith(selectedIndex: Int) {
        val newSort = SortOptions.values()[selectedIndex]
        if (newSort != selectedSort && definitions.value != null) {
            loading.value = true
            selectedSort = newSort
            definitions.value = definitions.value
            loading.value = false
        }
    }

    enum class SortOptions {
        THUMBS_UP, THUMBS_DOWN, DEFAULT
    }
}
