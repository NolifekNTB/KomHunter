package com.example.komhunter.uploadGPX.di

import com.example.komhunter.core.data.database.MapDatabase
import com.example.komhunter.uploadGPX.data.GpxRepository
import com.example.komhunter.uploadGPX.ui.GpxViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val gpxModule = module {
    single { MapDatabase.getDatabase(get()).gpxCoordinateDao() }
    single { GpxRepository(get()) }
    viewModel { GpxViewModel(get()) }
}