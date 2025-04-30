package com.aqeel.catsapp_kmm.di

import com.aqeel.catsapp_kmm.repository.CatsRepository
import com.aqeel.catsapp_kmm.repository.InMemoryCatsRepository
import com.aqeel.catsapp_kmm.viewmodel.CatsViewModel
import org.koin.dsl.module

val sharedModule = module {
    single<CatsRepository> { InMemoryCatsRepository() }
    factory { CatsViewModel(get()) }
} 