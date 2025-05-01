package com.aqeel.catsapp_kmm.di

import com.aqeel.catsapp_kmm.viewmodel.CatsViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class IosModule {
    fun doInitKoin() = initKoin()
}

object ViewModelProvider: KoinComponent {
    private val viewModel: CatsViewModel by inject()
    
    fun getCatsViewModel(): CatsViewModel = viewModel
} 