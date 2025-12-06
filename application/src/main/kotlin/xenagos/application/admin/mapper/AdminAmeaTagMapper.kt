package xenagos.application.admin.mapper

import org.springframework.stereotype.Component
import xenagos.application.port.input.admin.model.AdminAmeaTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAmeaTagResponseDTO
import xenagos.application.port.input.admin.model.AdminAmeaTagUpdateRequestDTO
import xenagos.domain.model.AmeaTag
import java.util.UUID

@Component
class AdminAmeaTagMapper : BaseAdminMapper<
        AmeaTag,
        AdminAmeaTagNewRequestDTO,
        AdminAmeaTagUpdateRequestDTO,
        AdminAmeaTagResponseDTO> {

    override fun toResponseDto(entity: AmeaTag): AdminAmeaTagResponseDTO =
        AdminAmeaTagResponseDTO(entity.id, entity.name, entity.description, entity.active)

    override fun toEntity(dto: AdminAmeaTagNewRequestDTO, id: UUID): AmeaTag =
        AmeaTag(id, dto.entityName, dto.description, dto.active)

    override fun toEntity(dto: AdminAmeaTagUpdateRequestDTO): AmeaTag =
        AmeaTag(dto.id, dto.entityName, dto.description, dto.active)
}