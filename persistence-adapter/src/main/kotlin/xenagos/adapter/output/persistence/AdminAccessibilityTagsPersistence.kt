package xenagos.adapter.output.persistence

import org.springframework.stereotype.Repository
import xenagos.application.port.output.AdminAccessibilityTagsOutputPort
import xenagos.domain.model.AccessibilityTag
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

@Repository
open class AdminAccessibilityTagsPersistence(val adminAccessibilityTagsRepository: AdminAccessibilityTagsRepository):
    AdminAccessibilityTagsOutputPort {
        override fun getAllAccessibilityTags(): ArrayList<AccessibilityTag> {
            val mockAccessibilityTagsList = ArrayList<AccessibilityTag>()
            repeat(10){mockAccessibilityTagsList.add(mockAccessibilityTag())}

            return mockAccessibilityTagsList
        }

    private fun mockAccessibilityTag():AccessibilityTag {
        return AccessibilityTag(
            id = UUID.randomUUID(),
            name = getRandomWords(3),
            description = getRandomWords(15)
        )
    }

    private fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")

    }

    private fun getRandomWords(words: Int): String {
        return (1..words)
            .map{getRandomString(Random.nextInt(15))}
            .joinToString(" ")
    }

}