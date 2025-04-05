package xenagos.application.port.input

import xenagos.application.port.input.model.AdminAccessibilityTagNewRequestDTO
import xenagos.application.port.input.model.AdminAccessibilityTagEditRequestDTO
import xenagos.application.port.input.model.AdminAccessibilityTagResponseDTO
import java.util.*
import kotlin.collections.ArrayList

interface AdminAccessibilityTagsUseCase {
    fun getAllAccessibilityTags(): ArrayList<AdminAccessibilityTagResponseDTO>
    fun saveNewAccessibilityTag(requestDTO: AdminAccessibilityTagNewRequestDTO): AdminAccessibilityTagResponseDTO
    fun updateAccessibilityTag(requestDTO: AdminAccessibilityTagEditRequestDTO): AdminAccessibilityTagResponseDTO
    fun deleteAccessibilityTag(id: UUID)
}