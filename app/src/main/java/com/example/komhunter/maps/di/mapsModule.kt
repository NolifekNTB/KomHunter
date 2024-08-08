package com.example.komhunter.maps.di

import com.example.komhunter.core.data.database.MapDatabase
import com.example.komhunter.maps.ui.MapsViewModel
import com.example.komhunter.uploadGPX.data.GpxRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mapsModule = module {
    viewModel{ MapsViewModel(get()) }
    single { GpxRepository(get()) }
}
