package com.razzdrawon.urbandictionary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.razzdrawon.urbandictionary.api.DefinitionsService
import com.razzdrawon.urbandictionary.model.Definition
import com.razzdrawon.urbandictionary.model.UrbanDictionaryResponse
import com.razzdrawon.urbandictionary.viewmodel.MainViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class MainViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var definitionsService: DefinitionsService

    @InjectMocks
    var mainViewModel = MainViewModel()

    private var testSingle: Single<UrbanDictionaryResponse>? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getDefinitionsSuccesss() {

        val definition = Definition(123, "hello", "hello world", "example hello", "Me", 1, 3, "vote")
        val definitionsList = arrayListOf(definition)
        val response = UrbanDictionaryResponse(definitionsList)

        testSingle = Single.just(response)

        `when`(definitionsService.getDefinitions("hello")).thenReturn(testSingle)

        mainViewModel.refresh("hello")

        Assert.assertEquals(1, mainViewModel.definitions.value?.size)
        Assert.assertEquals(false, mainViewModel.definitionsError.value)
        Assert.assertEquals(false, mainViewModel.loading.value)
    }

    @Test
    fun getDefinitionsFail() {

        testSingle = Single.error(Throwable())

        `when`(definitionsService.getDefinitions("hello")).thenReturn(testSingle)

        mainViewModel.refresh("hello")

        Assert.assertEquals(true, mainViewModel.definitionsError.value)
        Assert.assertEquals(false, mainViewModel.loading.value)
    }


    @Before
    fun setupRxSchedulers() {
        val immediate = object :Scheduler() {
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor {it.run()})
            }
        }
        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { schedules ->immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { schedules ->immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { schedules ->immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { schedules ->immediate }
    }

}