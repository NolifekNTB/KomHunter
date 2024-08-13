package com.example.komhunter.Route.UploadGPX.di

import com.example.komhunter.Route.UploadGPX.data.repositories.GpxRepository
import com.example.komhunter.Route.UploadGPX.ui.GpxViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val gpxModule = module {
    viewModel { GpxViewModel(get()) }
    single { GpxRepository(get()) }
}