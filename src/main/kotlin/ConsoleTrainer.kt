package org.example
import java.io.File

data class Word(
    val word: String,
    val translation: String,
    var correctAnswersCount: Int = 0
)

fun main() {
    val dictionary = loadDictionary()

    while (true) {
        println("\nМеню: ")
        println("1 – Учить слова")
        println("2 – Статистика")
        println("0 – Выход")

        when (readln()) {
            MENU_LEARN -> learnWords(dictionary)
            MENU_STATS -> showStatistics(dictionary)
            MENU_EXIT -> break
            else -> println("Введите число 1, 2 или 0")
        }
    }
}

fun loadDictionary(): MutableList<Word> {
    val dictionary = mutableListOf<Word>()
    val file = File(FILE_NAME)
    if (!file.exists()) {
        println("Файл $FILE_NAME не найден.")
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

fun saveDictionary(dictionary: List<Word>) {
    val file = File(FILE_NAME)
    file.writeText("")
    dictionary.forEach {
        file.appendText("${it.word} | ${it.translation} | ${it.correctAnswersCount}\n")
    }
}

fun showStatistics(dictionary: List<Word>) {
    val totalCount = dictionary.size
    val learnedWords = dictionary.filter { it.correctAnswersCount >= LEARNED_THRESHOLD }
    val learnedCount = learnedWords.size
    val percent = if (totalCount > 0) (learnedCount * 100 / totalCount) else 0
    println("\nВыучено $learnedCount из $totalCount слов | $percent%")
}

fun learnWords(dictionary: MutableList<Word>) {
    while (true) {
        val notLearned = dictionary.filter { it.correctAnswersCount < LEARNED_THRESHOLD }

        if (notLearned.isEmpty()) {
            println("Поздравляем! Все слова выучены.")
            return
        }

        var questionWords = notLearned.shuffled().take(OPTIONS_COUNT)
        if (questionWords.size < OPTIONS_COUNT) {
            val extras = dictionary.filter { it !in questionWords }.shuffled()
                .take(OPTIONS_COUNT - questionWords.size)
            questionWords = (questionWords + extras).shuffled()
        }

        val correctAnswer = questionWords.random()
        val correctAnswerId = questionWords.indexOf(correctAnswer) + 1

        val variants = questionWords
            .mapIndexed { index, word -> " ${index + 1} – ${word.translation}" }
            .joinToString(
                separator = "\n",
                prefix = "\n${correctAnswer.word}\n",
                postfix = "\n ----------\n 0 - Меню"
            )
        println(variants)

        print("> ")
        val userAnswerInput = readlnOrNull() ?: continue
        if (userAnswerInput == MENU_EXIT) break

        val userAnswer = userAnswerInput.toIntOrNull()
        if (userAnswer == null || userAnswer !in 1..OPTIONS_COUNT) {
            println("Введите число от 1 до $OPTIONS_COUNT или 0 для выхода в меню")
            continue
        }

        if (userAnswer == correctAnswerId) {
            println("Правильно!")
            correctAnswer.correctAnswersCount++
            saveDictionary(dictionary)
        } else {
            println("Неправильно! ${correctAnswer.word} – это ${correctAnswer.translation}")
        }
    }
}

const val LEARNED_THRESHOLD = 3
const val OPTIONS_COUNT = 4
const val MENU_EXIT = "0"
const val MENU_LEARN = "1"
const val MENU_STATS = "2"
const val FILE_NAME = "Dictionary.txt"