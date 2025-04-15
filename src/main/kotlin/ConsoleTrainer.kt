package ru.androidsprint.englishTrainer

fun main() {
    val storage = DictionaryStorage(FILE_NAME)
    val trainer = Trainer(storage)

    while (true) {
        printMenu()

        when (readlnOrNull()?.trim()) {
            "1" -> trainer.startLearning()
            "2" -> trainer.showStats()
            "0" -> {
                println("Пока!")
                return
            }
            else -> println("Введите число 1, 2 или 0")
        }
    }
}

const val LEARNED_THRESHOLD = 3
const val OPTIONS_COUNT = 4
const val FILE_NAME = "Dictionary.txt"