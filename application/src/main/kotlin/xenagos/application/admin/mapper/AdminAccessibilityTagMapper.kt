package xenagos.application.admin.mapper

import org.springframework.stereotype.Component
import xenagos.application.port.input.admin.model.AdminAccessibilityTagUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminAccessibilityTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAccessibilityTagResponseDTO
import xenagos.domain.model.AccessibilityTag
import java.util.UUID

@Component
class AdminAccessibilityTagMapper : AdminMapper<
        AccessibilityTag,
        AdminAccessibilityTagNewRequestDTO,
        AdminAccessibilityTagUpdateRequestDTO,
        AdminAccessibilityTagResponseDTO> {

     override fun toResponseDto(entity: AccessibilityTag): AdminAccessibilityTagResponseDTO =
        AdminAccessibilityTagResponseDTO(entity.id, entity.name, entity.description, entity.active)

    override fun toEntity(dto: AdminAccessibilityTagNewRequestDTO, id: UUID): AccessibilityTag =
        AccessibilityTag(id, dto.name, dto.description, dto.active)

    override fun toEntity(dto: AdminAccessibilityTagUpdateRequestDTO): AccessibilityTag =
        AccessibilityTag(dto.id, dto.name, dto.description, dto.active)
}