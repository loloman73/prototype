package xenagos.adapter.output.persistence

import kotlin.random.Random

class RandomText {
    companion object {
        fun getWords(words: Int): String {
            return (1..words)
                .map { getRandomString(Random.nextInt(15)) }
                .joinToString(" ")
        }
        private fun getRandomString(length: Int): String {
            val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
        }
    }
}