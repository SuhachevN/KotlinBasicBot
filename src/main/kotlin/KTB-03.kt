package org.example

import java.io.File

data class Word(
    val english: String,
    val russian: String,
    var correctAnswersCount: Int = 0,
)

fun main() {
    val wordsFile = File("Dictionary.txt")

    val dictionary = mutableListOf<Word>()

    wordsFile.forEachLine { line ->
        val parts = line.split("|")

        if (parts.size >= 3) {
            val english = parts[0]
            val russian = parts[1]

            val correctAnswersCount = parts.getOrNull(2)?.toIntOrNull() ?: 0

            dictionary.add(Word(english, russian, correctAnswersCount))
        }
    }

        for (word in dictionary) {
            println("${word.english}|${word.russian}|${word.correctAnswersCount}")
        }
}
