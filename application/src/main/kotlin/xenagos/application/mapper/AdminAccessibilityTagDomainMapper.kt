package xenagos.application.mapper

import org.springframework.stereotype.Component
import xenagos.application.port.input.model.AdminAccessibilityTagDTO
import xenagos.domain.model.AccessibilityTag

@Component
class AdminAccessibilityTagDomainMapper {
    fun entityToDTO(entity: AccessibilityTag): AdminAccessibilityTagDTO {
        return AdminAccessibilityTagDTO(entity.id, entity.name, entity.description, entity.active)
    }

    fun dtoToEntity(dto: AdminAccessibilityTagDTO): AccessibilityTag {
        return AccessibilityTag(dto.id, dto.accessibilityTag, dto.description, dto.active)
    }


}