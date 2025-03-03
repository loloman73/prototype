package xenagos.application.mapper

import xenagos.application.port.input.model.AdminAccessibilityTagDTO
import xenagos.domain.model.AccessibilityTag

class AdminAccessibilityTagMapper {
    fun entityToDTO(entity: AccessibilityTag): AdminAccessibilityTagDTO {
        return AdminAccessibilityTagDTO(entity.id, entity.name, entity.description)
    }

    fun dtoToEntity(dto: AdminAccessibilityTagDTO): AccessibilityTag {
        return AccessibilityTag(dto.accessibilityTagId, dto.accessibilityTag, dto.description)
    }


}