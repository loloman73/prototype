package xenagos.application.port.input.admin

import xenagos.application.port.input.admin.model.AdminAccessibilityTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAccessibilityTagEditRequestDTO
import xenagos.application.port.input.admin.model.AdminAccessibilityTagResponseDTO
import java.util.*
import kotlin.collections.ArrayList

interface AdminAccessibilityTagsUseCase {
    fun getAllAccessibilityTags(): ArrayList<AdminAccessibilityTagResponseDTO>
    fun saveNewAccessibilityTag(requestDTO: AdminAccessibilityTagNewRequestDTO): AdminAccessibilityTagResponseDTO
    fun updateAccessibilityTag(requestDTO: AdminAccessibilityTagEditRequestDTO): AdminAccessibilityTagResponseDTO
    fun deleteAccessibilityTag(id: UUID)
}