package com.example.komhunter.core.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Test

class WeatherForecastResponseTest {
    @Test
    fun `test getWeatherForecast success`() =
        runBlocking {
            val sampleResponse =
                """
                {
                    "cod":"200",
                    "message":0,
                    "cnt":2,
                    "list":[
                        {
                            "dt":1723809600,
                            "main":{
                                "temp":302.76,
                                "feels_like":303.64,
                                "temp_min":302.76,
                                "temp_max":303.72,
                                "pressure":1015,
                                "sea_level":1015,
                                "grnd_level":990,
                                "humidity":50,
                                "temp_kf":-0.96
                            },
                            "weather":[
                                {
                                    "id":802,
                                    "main":"Clouds",
                                    "description":"scattered clouds",
                                    "icon":"03d"
                                }
                            ],
                            "clouds":{"all":40},
                            "wind":{
                                "speed":4.45,
                                "deg":280,
                                "gust":5.87
                            },
                            "visibility":10000,
                            "pop":0,
                            "sys":{"pod":"d"},
                            "dt_txt":"2024-08-16 12:00:00"
                        },
                        {
                            "dt":1723820400,
                            "main":{
                                "temp":302.26,
                                "feels_like":302.59,
                                "temp_min":301.27,
                                "temp_max":302.26,
                                "pressure":1015,
                                "sea_level":1015,
                                "grnd_level":990,
                                "humidity":47,
                                "temp_kf":0.99
                            },
                            "weather":[
                                {
                                    "id":802,
                                    "main":"Clouds",
                                    "description":"scattered clouds",
                                    "icon":"03d"
                                }
                            ],
                            "clouds":{"all":50},
                            "wind":{
                                "speed":3.11,
                                "deg":259,
                                "gust":5.14
                            },
                            "visibility":10000,
                            "pop":0,
                            "sys":{"pod":"d"},
                            "dt_txt":"2024-08-16 15:00:00"
                        }
                    ],
                    "city":{
                        "id":3104116,
                        "name":"Łódź",
                        "coord":{"lat":51.7372,"lon":19.6423},
                        "country":"PL",
                        "population":3773,
                        "timezone":7200,
                        "sunrise":1723778906,
                        "sunset":1723831390
                    }
                }
                """.trimIndent()

            val mockEngine =
                MockEngine { request ->
                    respond(
                        content = sampleResponse,
                        status = HttpStatusCode.OK,
                        headers = headersOf("Content-Type", "application/json"),
                    )
                }

            val mockClient =
                HttpClient(mockEngine) {
                    install(ContentNegotiation) {
                        json(Json { ignoreUnknownKeys = true })
                    }
                }

            val weatherForecastResponse = WeatherForecastResponse(mockClient)
            val result = weatherForecastResponse.getWeatherForecast("35.6895", "139.6917")

            // Validate the result
            assertEquals("Łódź", result.city.name) // TODO: change
            assertEquals(2, result.list.size)

            // Check the first forecast
            val firstForecast = result.list[0]
            assertEquals(1723809600, firstForecast.dt)
            assertEquals(302.76, firstForecast.main.temp, 0.01)
            assertEquals("Clouds", firstForecast.weather[0].main)
            assertEquals("scattered clouds", firstForecast.weather[0].description)

            // Check the second forecast
            val secondForecast = result.list[1]
            assertEquals(1723820400, secondForecast.dt)
            assertEquals(302.26, secondForecast.main.temp, 0.01)
            assertEquals("Clouds", secondForecast.weather[0].main)
            assertEquals("scattered clouds", secondForecast.weather[0].description)
        }
}
