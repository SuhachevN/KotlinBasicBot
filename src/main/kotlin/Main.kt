package org.example

import java.io.File

fun main() {


    val wordsFile: File = File("Dictionary.txt")

    val lines = wordsFile.readLines()

    wordsFile.forEachLine { line ->
        println(line)
    }
}