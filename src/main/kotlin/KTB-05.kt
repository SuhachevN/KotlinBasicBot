package org.example

import java.io.File

fun loadDictionary(fileName: String): List<Word> {
    val file = File(fileName)
    return file.useLines { lines ->
        lines.mapNotNull { line ->
            val parts = line.split("|")
            if (parts.size >= 3) {
                Word(parts[0], parts[1], parts.getOrElse(2) { "0" }.toInt())
            } else {
                null
            }
        }.toList()
    }
}
fun main() {
    val dictionary = loadDictionary("Dictionary.txt")

    while (true) {
        println("\nМеню:")
        println("1 - Учить слова")
        println("2 - Статистика")
        println("0 - Выход")

        print("Ваш выбор: ")
        val input = readLine()?.trim()

        when (input) {
            "1" -> {
                println("Вы выбрали 'Учить слова'.")
            }

            "2" -> {
                println("Вы выбрали 'Статистика'.")
            }

            "0" -> {
                println("Завершение программы...")
                break
            }

            else -> {
                println("Введите число 1, 2 или 0.")
            }
        }
    }
}

fun studyWords(words: List<Word>) {
    println("Изучение слов:")
    for ((index, word) in words.withIndex()) {
        println("$index. ${word.english} — ${word.russian}")
    }
}

fun showStatistics(words: List<Word>) {
    println("Статистика:")
    for (word in words) {
        println("${word.english}: ${word.correctAnswersCount} правильных ответов")
    }
}