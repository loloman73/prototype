package xenagos.adapter.output.persistence

import org.springframework.stereotype.Repository
import xenagos.application.port.output.AdminAccessibilityTagsOutputPort
import xenagos.domain.model.AccessibilityTag
import java.util.*
import kotlin.collections.ArrayList

@Repository
open class AdminAccessibilityTagsPersistence(
    val repository: AdminAccessibilityTagsRepository
) : AdminAccessibilityTagsOutputPort {

    override fun getAllAccessibilityTags(): ArrayList<AccessibilityTag> {
        val mockAccessibilityTagsList = arrayListOf<AccessibilityTag>()
        repeat(10) { mockAccessibilityTagsList.add(mockAccessibilityTag()) }

        return mockAccessibilityTagsList
    }

    private fun mockAccessibilityTag(): AccessibilityTag {
        return AccessibilityTag(
            id = UUID.randomUUID(),
            name = RandomText.getWords(3),
            description = RandomText.getWords(15)
        )
    }

}