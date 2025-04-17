package ru.androidsprint.englishTrainer

class Trainer(storage: DictionaryStorage) {

    private val dictionary = storage.load()

    fun startLearning() {
        while (true) {
            val notLearned = dictionary.filter { it.correctAnswersCount < LEARNED_THRESHOLD }

            if (notLearned.isEmpty()) {
                println("Поздравляем! Все слова выучены.")
                return
            }

            var questionWords = notLearned.randomSelection(OPTIONS_COUNT)
            if (questionWords.size < OPTIONS_COUNT) {
                val extras = dictionary.filter { it.correctAnswersCount >= LEARNED_THRESHOLD }.shuffled()
                    .take(OPTIONS_COUNT - questionWords.size)
                questionWords = (questionWords + extras).shuffled()
            }

            val correctAnswer = questionWords.random()
            val variants = questionWords.formatQuestion(correctAnswer)
            println(variants)
        }
    }

    fun getStatistics(): Statistic {
        val learned = dictionary.count { it.correctAnswersCount >= LEARNED_THRESHOLD }
        val total = dictionary.size
        return Statistic(learned, total)
    }
}


