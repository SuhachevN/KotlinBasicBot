package org.example

fun saveDictionary(dictionary: List<Word>, fileName: String) {
    val file = File(fileName)
    file.printWriter().use { writer ->
        dictionary.forEach {
            writer.println("${it.english}|${it.russian}|${it.correctAnswersCount}")
        }
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

                val notLearnedList = dictionary.filter { it.correctAnswersCount < 3 }

                if (notLearnedList.isEmpty()) {
                    println("Все слова в словаре выучены!")
                    continue
                }

                var isContinueLearning = true
                while (isContinueLearning && notLearnedList.isNotEmpty()) {

                    val currentWord = notLearnedList.random()

                    val questionWords = mutableListOf(currentWord)
                    repeat(3) {
                        val newWord = notLearnedList.random()
                        if (!questionWords.contains(newWord)) {
                            questionWords.add(newWord)
                        }
                    }

                    questionWords.shuffle()

                    println("\n${currentWord.english}:")
                    for ((index, word) in questionWords.withIndex()) {
                        println("${index + 1} - ${word.russian}")
                    }

                    println("----------")
                    println("0 - Вернуться в меню")

                    print("Ваш ответ: ")
                    val userInput = readLine()?.trim()?.toIntOrNull()

                    if (userInput == 0) {
                        println("Возвращение в главное меню...")
                        isContinueLearning = false
                    } else if (userInput != null && userInput in 1..4) {
                        val selectedWord = questionWords[userInput - 1]

                        if (selectedWord.english == currentWord.english) {
                            println("Правильно!")
                            currentWord.correctAnswersCount++

                            saveDictionary(dictionary, "Dictionary.txt")

                            if (currentWord.correctAnswersCount >= 3) {
                                notLearnedList.remove(currentWord)
                            }
                        } else {
                            println("Неправильно! ${currentWord.english} – это ${currentWord.russian}.")
                        }
                    } else {
                        println("Введен неверный номер ответа. Попробуйте еще раз.")
                    }

                    if (notLearnedList.isEmpty()) {
                        println("Поздравляем! Все слова выучены!")
                        break
                    }
                }

                if (notLearnedList.isEmpty()) {
                    println("Поздравляем! Все слова выучены!")
                }
            }

            "2" -> {
                val totalCount = dictionary.size
                val learnedCount = dictionary.filter { it.correctAnswersCount >= 3 }.size
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