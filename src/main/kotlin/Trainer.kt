package ru.androidsprint.englishTrainer

class Trainer(private val storage: DictionaryStorage) {

    val dictionary = storage.load()

    fun startLearning() {
        while (true) {
            val notLearned = dictionary.filter { it.correctAnswersCount < LEARNED_THRESHOLD }

            if (notLearned.isEmpty()) {
                println("Поздравляем! Все слова выучены.")
                return
            }

            var questionWords = notLearned.randomSelection(OPTIONS_COUNT)
            if (questionWords.size < OPTIONS_COUNT) {
                val extras = dictionary.filter { it !in questionWords }.shuffled()
                    .take(OPTIONS_COUNT - questionWords.size)
                questionWords = (questionWords + extras).shuffled()
            }

            val correctAnswer = questionWords.random()
            val correctIndex = questionWords.indexOf(correctAnswer) + 1

            val variants = questionWords.formatQuestion(correctAnswer)
            println(variants)

            print("> ")
            val input = readlnOrNull()?.trim()
            if (input == "0") return

            val answer = input?.toIntOrNull()
            if (answer == null || answer !in 1..OPTIONS_COUNT) {
                println("Введите число от 1 до $OPTIONS_COUNT или 0 для выхода в меню")
                continue
            }

            if (answer == correctIndex) {
                println("Правильно!")
                correctAnswer.correctAnswersCount++
                storage.save(dictionary)
            } else {
                println("Неправильно! ${correctAnswer.word} – это ${correctAnswer.translation}")
            }
        }
    }

    fun showStats() {
        val learned = dictionary.count { it.correctAnswersCount >= LEARNED_THRESHOLD }
        val total = dictionary.size
        val percent = learned.toPercentOf(total)
        println("\nВыучено $learned из $total слов | $percent%")
    }
}


