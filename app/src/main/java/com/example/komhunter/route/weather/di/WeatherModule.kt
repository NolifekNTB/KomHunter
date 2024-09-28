package com.example.komhunter.route.weather.di

import com.example.komhunter.core.data.network.WeatherForecastResponse
import com.example.komhunter.route.uploadGPX.data.repositories.GpxRepository
import com.example.komhunter.route.weather.data.repositories.ForecastRepository
import com.example.komhunter.route.weather.data.repositories.WeatherRepository
import com.example.komhunter.route.weather.ui.WeatherViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var weatherModule =
    module {
        viewModel { WeatherViewModel(get(), get(), get()) }
        single { WeatherRepository(get()) }
        single { ForecastRepository(get()) }
        single { WeatherForecastResponse(get()) }
        single { GpxRepository(get()) }

        single {
            HttpClient(CIO) {
                install(Logging) {
                    level = LogLevel.ALL
                }
                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                            isLenient = true
                            prettyPrint = true
                            useAlternativeNames = false
                        },
                    )
                }
            }
        }
    }
