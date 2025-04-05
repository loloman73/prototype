package xenagos.application.port.output

import xenagos.domain.model.AccessibilityTag
import java.util.*
import kotlin.collections.ArrayList

interface AdminAccessibilityTagsOutputPort {
    fun getAllAccessibilityTags(): ArrayList<AccessibilityTag>
    fun saveNewAccessibilityTag(newEntityToSave: AccessibilityTag): AccessibilityTag
    fun updateAccessibilityTag(entityToUpdate: AccessibilityTag): AccessibilityTag
    fun deleteAccessibilityTag(id: UUID)
}