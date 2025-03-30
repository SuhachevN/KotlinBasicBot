package org.example

import java.io.File

fun main() {

    val wordsFile: File = File("Dictionary.txt")

    wordsFile.forEachLine { line ->
        println(line)
    }
}