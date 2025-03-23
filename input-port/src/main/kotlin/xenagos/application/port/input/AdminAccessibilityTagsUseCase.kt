package xenagos.application.port.input

import xenagos.application.port.input.model.AdminAccessibilityTagDTO
import xenagos.application.port.input.model.AdminAccessibilityTagNewDTO
import java.util.*
import kotlin.collections.ArrayList

interface AdminAccessibilityTagsUseCase {
    fun getAllAccessibilityTags(): ArrayList<AdminAccessibilityTagDTO>
    fun saveNewAccessibilityTag(adminNewAccessibilityTag: AdminAccessibilityTagNewDTO): AdminAccessibilityTagDTO
    fun updateAccessibilityTag(accessibilityTagDTO: AdminAccessibilityTagDTO): AdminAccessibilityTagDTO
    fun deleteAccessibilityTag(accessibilityTagId: UUID)
}