package ru.androidsprint.englishTrainer

fun Int.toPercentOf(total: Int): Int =
    if (total == 0) 0 else (this * 100) / total

fun printMenu() {
    println("\nМеню: ")
    println("1 – Учить слова")
    println("2 – Статистика")
    println("0 – Выход")
}

fun List<Word>.formatQuestion(correctAnswer: Word): String {
    return buildString {
        appendLine("\nПереведи: ${correctAnswer.word}")
        this@formatQuestion.forEachIndexed { index, word ->
            appendLine(" ${index + 1} – ${word.translation}")
        }
        appendLine(" ----------\n 0 - Меню")
    }
}