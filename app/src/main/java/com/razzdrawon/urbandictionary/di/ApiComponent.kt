package com.razzdrawon.urbandictionary.di

import com.razzdrawon.urbandictionary.api.DefinitionsService
import com.razzdrawon.urbandictionary.viewmodel.MainViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject (service: DefinitionsService)

    fun inject (viewModel: MainViewModel)

}