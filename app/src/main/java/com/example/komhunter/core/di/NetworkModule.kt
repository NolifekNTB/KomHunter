package com.example.komhunter.core.di

import com.example.komhunter.Weather.data.WeatherRepository
import com.example.komhunter.Weather.ui.WeatherViewModel
import com.example.komhunter.core.data.database.MapDatabase
import com.example.komhunter.core.data.network.ResponseService
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var networkModule = module {
    viewModel { WeatherViewModel(get()) }
    single { WeatherRepository(get(), get()) }
        single { ResponseService(get()) }
        single { MapDatabase.getDatabase(get()).weatherDao() }

    single {
        HttpClient(CIO) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                    useAlternativeNames = false
                })
            }
        }
    }
}