package ru.androidsprint.englishTrainer

fun main() {
    val trainer = LearnWordsTrainer()

    while (true) {
        printMenu()

        when (readlnOrNull()?.trim()) {
            "1" -> {
                var question: Question?
                while (true) {
                    question = trainer.getNextQuestion()
                    if (question == null) {
                        println("Поздравляем! Все слова выучены.")
                        break
                    }
                    println(question.variants.formatQuestion(question.correctAnswer))

                    val userAnswer = readlnOrNull()?.toIntOrNull()
                    if (userAnswer == 0) break

                    val isCorrect = trainer.checkAnswer(userAnswer)
                    if (isCorrect) {
                        println("Правильно!")
                    } else {
                        println("Неправильно!")
                    }
                }
            }
            "2" -> {
                val stats = trainer.getStatistics()
                println("\nВыучено ${stats.learnedCount} из ${stats.totalCount} слов | ${stats.percent}%")
            }
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