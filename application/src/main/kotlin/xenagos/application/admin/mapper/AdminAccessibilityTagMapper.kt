package xenagos.application.admin.mapper

import xenagos.application.port.input.admin.model.AdminAccessibilityTagUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminAccessibilityTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAccessibilityTagResponseDTO
import xenagos.domain.model.AccessibilityTag
import java.util.UUID

fun AccessibilityTag.toResponseDto() =
    AdminAccessibilityTagResponseDTO(this.id, this.name, this.description, this.active)

fun AdminAccessibilityTagNewRequestDTO.toEntity(id: UUID) =
    AccessibilityTag(id, this.name, this.description, this.active)

fun AdminAccessibilityTagUpdateRequestDTO.toEntity() =
    AccessibilityTag(this.id, this.name, this.description, this.active)