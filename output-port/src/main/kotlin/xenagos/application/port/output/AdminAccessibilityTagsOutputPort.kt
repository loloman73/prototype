package xenagos.application.port.output

import xenagos.domain.model.AccessibilityTag
import java.util.*
import kotlin.collections.ArrayList

interface AdminAccessibilityTagsOutputPort {
    fun getAllAccessibilityTags(): ArrayList<AccessibilityTag>
    fun saveNewAccessibilityTag(accessibilityTag: AccessibilityTag): AccessibilityTag
    fun updateAccessibilityTag(accessibilityTag: AccessibilityTag): AccessibilityTag
    fun deleteAccessibilityTag(accessibilityTagId: UUID)
}