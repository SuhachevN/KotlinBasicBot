package ru.androidsprint.englishTrainer

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun main(args: Array<String>) {
    val botToken = args[0]
    val urlGetMe = "https://api.telegram.org/bot$botToken/getMe"
    val urlGetUpdates = "https://api.telegram.org/bot$botToken/getUpdates"

    val client: HttpClient = HttpClient.newBuilder().build()

    val requestGetMe: HttpRequest = HttpRequest.newBuilder()
        .uri(URI.create(urlGetMe))
        .build()

    val responseGetMe: HttpResponse<String> = client.send(requestGetMe, HttpResponse.BodyHandlers.ofString())

    val json = Json { ignoreUnknownKeys = true }

    val getMeResponse = json.decodeFromString<GetMeResponse>(responseGetMe.body())

    println("Bot Info: ${getMeResponse.result.first_name} (@${getMeResponse.result.username})")

    val requestGetUpdates: HttpRequest = HttpRequest.newBuilder()
        .uri(URI.create(urlGetUpdates))
        .build()

    val responseGetUpdates: HttpResponse<String> = client.send(requestGetUpdates, HttpResponse.BodyHandlers.ofString())
    val getUpdatesResponse = json.decodeFromString<GetUpdatesResponse>(responseGetUpdates.body())

    if (getUpdatesResponse.ok) {
        for (update in getUpdatesResponse.result) {
            println("Update ID: ${update.update_id}")
            update.message?.let {
                println("${it.from.first_name} (${it.from.username}): ${it.text}")
            }
        }
    } else {
        println("Ошибка при получении обновлений.")
    }
}