package xenagos.application.admin.mapper

import org.springframework.stereotype.Component
import xenagos.application.port.input.admin.model.AdminAgeGroupUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupResponseDTO
import xenagos.domain.model.AgeGroup
import java.util.UUID

@Component
class AdminAgeGroupMapper : BaseAdminMapper<
        AgeGroup,
        AdminAgeGroupNewRequestDTO,
        AdminAgeGroupUpdateRequestDTO,
        AdminAgeGroupResponseDTO> {

    override fun toResponseDto(entity: AgeGroup): AdminAgeGroupResponseDTO =
        AdminAgeGroupResponseDTO(entity.id, entity.name, entity.minAge, entity.maxAge, entity.active)

    override fun toEntity(dto: AdminAgeGroupNewRequestDTO, id: UUID): AgeGroup =
        AgeGroup(id, dto.entityName, dto.minAge, dto.maxAge, dto.active)

    override fun toEntity(dto: AdminAgeGroupUpdateRequestDTO): AgeGroup =
        AgeGroup(dto.id, dto.entityName, dto.minAge, dto.maxAge, dto.active)
}