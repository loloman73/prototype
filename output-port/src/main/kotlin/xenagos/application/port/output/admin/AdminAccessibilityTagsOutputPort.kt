package xenagos.application.port.output.admin

import xenagos.domain.model.AccessibilityTag
import java.util.*

interface AdminAccessibilityTagsOutputPort {
    fun getAllAccessibilityTags(): ArrayList<AccessibilityTag>
    fun saveNewAccessibilityTag(newEntityToSave: AccessibilityTag): AccessibilityTag
    fun updateAccessibilityTag(entityToUpdate: AccessibilityTag): AccessibilityTag
    fun deleteAccessibilityTag(id: UUID)
}