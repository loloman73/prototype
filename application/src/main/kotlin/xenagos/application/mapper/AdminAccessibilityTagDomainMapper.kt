package xenagos.application.mapper

import org.springframework.stereotype.Component
import xenagos.application.port.input.model.AdminAccessibilityTagResponseDTO
import xenagos.domain.model.AccessibilityTag

@Component
class AdminAccessibilityTagDomainMapper {
    fun entityToDTO(entity: AccessibilityTag): AdminAccessibilityTagResponseDTO {
        return AdminAccessibilityTagResponseDTO(entity.id, entity.name, entity.description, entity.active)
    }

    fun dtoToEntity(dto: AdminAccessibilityTagResponseDTO): AccessibilityTag {
        return AccessibilityTag(dto.id, dto.accessibilityTag, dto.description, dto.active)
    }


}