package xenagos.application.mapper

import xenagos.application.port.input.model.AdminAccessibilityTagEditRequestDTO
import xenagos.application.port.input.model.AdminAccessibilityTagNewRequestDTO
import xenagos.application.port.input.model.AdminAccessibilityTagResponseDTO
import xenagos.domain.model.AccessibilityTag
import java.util.UUID

fun AccessibilityTag.toResponseDto() =
    AdminAccessibilityTagResponseDTO(this.id, this.name, this.description, this.active)

fun AdminAccessibilityTagNewRequestDTO.toEntity(id: UUID) =
    AccessibilityTag(id, this.name, this.description, this.active)

fun AdminAccessibilityTagEditRequestDTO.toEntity() =
    AccessibilityTag(this.id, this.name, this.description, this.active)