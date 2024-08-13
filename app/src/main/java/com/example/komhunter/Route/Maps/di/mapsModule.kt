package com.example.komhunter.Route.Maps.di

import com.example.komhunter.Route.Maps.ui.MapsViewModel
import com.example.komhunter.Route.UploadGPX.data.repositories.GpxRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mapsModule = module {
    viewModel{ MapsViewModel(get()) }
    single { GpxRepository(get()) }
}
