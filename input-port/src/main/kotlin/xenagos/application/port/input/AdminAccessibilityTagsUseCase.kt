package xenagos.application.port.input

import xenagos.application.port.input.model.AdminAccessibilityTagDTO

interface AdminAccessibilityTagsUseCase {
    fun getAllAccessibilityTags(): ArrayList<AdminAccessibilityTagDTO>
}