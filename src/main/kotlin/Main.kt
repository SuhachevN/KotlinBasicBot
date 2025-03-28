package org.example

import java.io.File

fun main() {

    val wordsFile: File = File("Dictionary.txt")
    wordsFile.createNewFile()

    val wordsAndTranslations = listOf(
        "hello привет",
        "dog собака",
        "cat кошка"
    )
    wordsFile.writeText(wordsAndTranslations.joinToString("\n"))

    val lines = wordsFile.readLines()

    for (line in lines) {
        println(line)
    }
}