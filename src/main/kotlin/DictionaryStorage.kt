package ru.androidsprint.englishTrainer

import java.io.File

class DictionaryStorage(private val fileName: String) {

    fun load(): MutableList<Word> {
        val dictionary = mutableListOf<Word>()
        val file = File(fileName)
        if (!file.exists()) {
            println("Файл $fileName не найден.")
            return dictionary
        }

        file.forEachLine { line ->
            val parts = line.split("|").map { it.trim() }
            val word = parts.getOrNull(0) ?: return@forEachLine
            val translation = parts.getOrNull(1) ?: return@forEachLine
            val correctAnswers = parts.getOrNull(2)?.toIntOrNull() ?: 0
            dictionary.add(Word(word, translation, correctAnswers))
        }

        return dictionary
    }

    fun save(dictionary: List<Word>) {
        if (dictionary.isEmpty()) return

        val file = File(fileName)
        file.bufferedWriter().use { writer ->
            dictionary.forEach {
                writer.write("${it.word} | ${it.translation} | ${it.correctAnswersCount}\n")
            }
        }
    }
}


