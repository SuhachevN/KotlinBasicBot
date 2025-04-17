package ru.androidsprint.englishTrainer

data class Word(
    val word: String,
    val translation: String,
    var correctAnswersCount: Int = 0
)