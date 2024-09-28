package com.example.komhunter.route.maps.di

import com.example.komhunter.route.maps.ui.MapsViewModel
import com.example.komhunter.route.uploadGPX.data.repositories.GpxRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val MapsModule =
    module {
        viewModel { MapsViewModel(get()) }
        single { GpxRepository(get()) }
    }
