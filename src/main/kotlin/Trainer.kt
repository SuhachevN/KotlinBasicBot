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

class LearnWordsTrainer(private val learnedAnswerCount: Int = 3, private val countOfQuestionsWords: Int = 4) {
    private var question: Question? = null
    private val dictionary = loadDictionary()

    fun getStatistics(): Statistics {
        val totalCount = dictionary.size
        val learnedCount = dictionary.count { it.correctAnswersCount >= learnedAnswerCount }
        val percent = learnedCount.toPercentOf(totalCount)
        return Statistics(totalCount, learnedCount, percent)
    }

    fun getNextQuestion(): Question? {
        val notLearnedList = dictionary.filter { it.correctAnswersCount < learnedAnswerCount }
        if (notLearnedList.isEmpty()) return null
        val questionWords = if (notLearnedList.size < countOfQuestionsWords) {
            val learnedList = dictionary.filter {it.correctAnswersCount >= learnedAnswerCount}.shuffled()
            notLearnedList.shuffled().take(countOfQuestionsWords) + learnedList.take(countOfQuestionsWords - notLearnedList.size)
        } else {
            notLearnedList.shuffled().take(countOfQuestionsWords)
        }.shuffled()

        val correctAnswer = questionWords.random()
        question = Question(
            variants = questionWords,
            correctAnswer = correctAnswer,
        )
        return question
    }

    fun checkAnswer(userAnswerIndex: Int?): Boolean {
        val currentQuestion = question ?: return false
        val correctAnswerId = currentQuestion.variants.indexOf(currentQuestion.correctAnswer)

        if (userAnswerIndex != null && correctAnswerId != -1 && userAnswerIndex == correctAnswerId + 1) {
            currentQuestion.correctAnswer.correctAnswersCount++
            saveDictionary(dictionary)
            return true
        }
        return false
    }

    private fun loadDictionary(): List<Word> {
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
    }

    private fun saveDictionary(words: List<Word>) {
        val wordsFile = File(FILE_NAME)
        wordsFile.writeText("")
        for (word in words) {
            wordsFile.appendText("${word.word}|${word.translation}|${word.correctAnswersCount}\n")
        }
    }

}