package xenagos.application.mapper

import org.springframework.stereotype.Component
import xenagos.application.port.input.model.AdminAccessibilityTagEditRequestDTO
import xenagos.application.port.input.model.AdminAccessibilityTagNewRequestDTO
import xenagos.application.port.input.model.AdminAccessibilityTagResponseDTO
import xenagos.domain.model.AccessibilityTag
import java.util.UUID

@Component
class AdminAccessibilityTagDomainMapper {

    fun entityToRespDto(entity: AccessibilityTag): AdminAccessibilityTagResponseDTO {
        return AdminAccessibilityTagResponseDTO(entity.id, entity.name, entity.description, entity.active)
    }

    fun editReqDtoToEntity(dto: AdminAccessibilityTagEditRequestDTO): AccessibilityTag {
        return AccessibilityTag(dto.id, dto.accessibilityTag, dto.description, dto.active)
    }

    fun newReqDtoToEntity(dto: AdminAccessibilityTagNewRequestDTO, id: UUID): AccessibilityTag {
        return AccessibilityTag(id, dto.accessibilityTag, dto.description, dto.active)
    }
}