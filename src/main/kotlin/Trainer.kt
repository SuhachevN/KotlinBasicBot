package ru.androidsprint.englishTrainer

import java.io.File

class Statistics(
    val totalCount: Int,
    val learnedCount: Int,
    val percent: Int,
)

data class Question(
    val variants: List<Word>,
    val correctAnswer: Word,
)

class LearnWordsTrainer {

    private var question: Question? = null
    private val dictionary = loadDictionary()

    fun getStatistics(): Statistics {
        val totalCount = dictionary.size
        val learnedCount = dictionary.count { it.correctAnswersCount >= LEARNED_THRESHOLD }
        val percent = learnedCount.toPercentOf(totalCount)
        return Statistics(totalCount, learnedCount, percent)
    }

    fun getNextQuestion(): Question? {
        val notLearnedList = dictionary.filter { it.correctAnswersCount < LEARNED_THRESHOLD }
        if (notLearnedList.isEmpty()) return null
        val questionWords = notLearnedList.randomSelection(OPTIONS_COUNT)
        val correctAnswer = questionWords.random()
        question = Question(
            variants = questionWords,
            correctAnswer = correctAnswer,
        )
        return question
    }

    fun checkAnswer(userAnswerIndex: Int?): Boolean {
        return question?.let {
            val correctAnswerId = it.variants.indexOf(it.correctAnswer)
            if (correctAnswerId == userAnswerIndex) {
                it.correctAnswer.correctAnswersCount++
                saveDictionary(dictionary)
                true
            } else {
                false
            }
        } ?: false
    }

    private fun loadDictionary(): List<Word> {
        try {
            val dictionary = mutableListOf<Word>()
            val wordsFile = File(FILE_NAME)

            for (string in wordsFile.readLines()) {
                val split = string.split("|")
                val word = Word(
                    word = split[0],
                    translation = split[1],
                    correctAnswersCount = split[2].toIntOrNull() ?: 0
                )
                dictionary.add(word)
            }
            return dictionary
        } catch (e: Exception) {
            throw IllegalStateException("Некорректный файл: $e")
        }
    }

    private fun saveDictionary(words: List<Word>) {
        val wordsFile = File(FILE_NAME)
        wordsFile.writeText("")
        for (word in words) {
            wordsFile.appendText("${word.word}|${word.translation}|${word.correctAnswersCount}\n")
        }
    }

}
