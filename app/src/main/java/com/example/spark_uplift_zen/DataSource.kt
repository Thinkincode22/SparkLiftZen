package com.example.spark_uplift_zen

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Quote(val text: String, val author: String?)

suspend fun getQuotes(): List<Quote> {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    return try {
        client.get("https://type.fit/api/quotes").body()
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    } finally {
        client.close()
    }
}
