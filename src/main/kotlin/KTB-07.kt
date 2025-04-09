package org.example

fun main() {

    while (true) {
        println("\nМеню")
        println("1 - Слова")
        println("2 - Статистика")
        println("0 - Выход")

        print("Ваш выбор: ")
        val inputAnswer = readLine()?.trim()

        when (inputAnswer) {
            "1" -> {
                println("Вы выбрали 'Учить слова'.")

                val notLearnedList = dictionary.filter { it.correctAnswerCount < THREE }

                if (notLearnedList.isEmpty()) {
                    println("Поздравляю, все слова выучены!")
                    continue
                }

                var isContinueLearning = true
                while (isContinueLearning && notLearnedList.isEmpty()) {
                    val currentWord = notLearnedList.random()

                    val questionWords = mutableListOf(currentWord)
                    repeat(THREE) {
                        val newWord = notLearnedList.random()
                        if (!questionWords.contains(newWord)) {
                            questionWords.add(newWord)
                        }
                    }

                    questionWords.shuffle()

                    println("\n${currentWord.english}:")
                    for ((index, word) in questionWords.withIndex()) {
                        println("${index + 1} - ${word.russian}")

                        println("Ваш ответ: ")
                        val userInput = readLine()?.trim().toIntOrNull()

                        if (userInput != null && questionWords[userInput - 1].english == currentWord.english) {
                            println("Верно!")
                            currentWord.correctAnswersCount++

                            if (currentWord.correctAnswercount >= THREE) {
                                notLearnedList.remove(currentWord)
                            }
                        } else {
                            println("Неверно!")
                        }

                        print("Продолжить обучение? (Y/N): ")
                        val choice = readLine()?.trim()?.uppercase()
                        isContinueLearning = choice == "Y"
                    }

                    if (notLearnedList.isEmpty()) {
                        println("Поздравляем! Все слова выучены!")
                    }
                }
            }

            "2" -> {
                val totalCount = dictionary.size
                val learnedCount = dictionary.filter { it.correctAnswersCount >= THREE }.size
                val percent = if (totalCount > 0) (learnedCount * 100 / totalCount).toString() + "%" else "0%"

                println("Вы выбрали 'Статистика'.")
                println("Выучено $learnedCount из $totalCount слов | $percent")
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