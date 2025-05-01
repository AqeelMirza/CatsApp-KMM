package com.aqeel.catsapp_kmm.android.di

import com.aqeel.catsapp_kmm.viewmodel.AndroidCatsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    viewModel { AndroidCatsViewModel(get()) }
} 