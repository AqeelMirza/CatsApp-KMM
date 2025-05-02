package com.aqeel.catsapp_kmm.di

import com.aqeel.catsapp_kmm.viewmodel.CatsViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun initKoinIos() = initKoin {}

class IosModule {
    fun doInitKoin() = initKoinIos()
}

object ViewModelProvider: KoinComponent {
    private val viewModel: CatsViewModel by inject()
    
    fun getCatsViewModel(): CatsViewModel = viewModel
} 