package com.example.komhunter.route.uploadGPX.di

import com.example.komhunter.route.uploadGPX.data.repositories.GpxRepository
import com.example.komhunter.route.uploadGPX.ui.GpxViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val GpxModule =
    module {
        viewModel { GpxViewModel(get()) }
        single { GpxRepository(get()) }
    }
