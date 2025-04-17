package ru.androidsprint.englishTrainer

data class Statistic(
    val learned: Int,
    val total: Int
) {
    val percent: Int get() = learned.toPercentOf(total)
}
