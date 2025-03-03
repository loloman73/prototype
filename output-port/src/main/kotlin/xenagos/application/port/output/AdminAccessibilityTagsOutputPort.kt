package xenagos.application.port.output

import xenagos.domain.model.AccessibilityTag

interface AdminAccessibilityTagsOutputPort {
    fun getAllAccessibilityTags(): ArrayList<AccessibilityTag>
}