package xenagos.application.admin.mapper

import org.springframework.stereotype.Component
import xenagos.application.port.input.admin.model.AdminMediaTypeUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeNewRequestDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeResponseDTO
import xenagos.domain.model.MediaType
import java.util.UUID

@Component
class AdminMediaTypeMapper : BaseAdminMapper<
        MediaType,
        AdminMediaTypeNewRequestDTO,
        AdminMediaTypeUpdateRequestDTO,
        AdminMediaTypeResponseDTO> {

    override fun toResponseDto(entity: MediaType): AdminMediaTypeResponseDTO =
        AdminMediaTypeResponseDTO(entity.id, entity.name, entity.active)

    override fun toEntity(dto: AdminMediaTypeNewRequestDTO, id: UUID): MediaType =
        MediaType(id, dto.entityName, dto.active)

    override fun toEntity(dto: AdminMediaTypeUpdateRequestDTO): MediaType =
        MediaType(dto.id, dto.entityName, dto.active)
}