package org.example

fun main() {
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
const val THREE = 3