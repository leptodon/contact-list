package ru.cactus.contactlist.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.cactus.contactlist.ui.viewmodels.MainViewModel

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}