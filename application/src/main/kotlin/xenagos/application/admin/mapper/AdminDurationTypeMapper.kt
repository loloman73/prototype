package xenagos.application.admin.mapper

import org.springframework.stereotype.Component
import xenagos.application.port.input.admin.model.AdminDurationTypeNewRequestDTO
import xenagos.application.port.input.admin.model.AdminDurationTypeResponseDTO
import xenagos.application.port.input.admin.model.AdminDurationTypeUpdateRequestDTO
import xenagos.domain.model.DurationType
import java.util.UUID

@Component
class AdminDurationTypeMapper : BaseAdminMapper<
        DurationType,
        AdminDurationTypeNewRequestDTO,
        AdminDurationTypeUpdateRequestDTO,
        AdminDurationTypeResponseDTO> {

    override fun toResponseDto(entity: DurationType): AdminDurationTypeResponseDTO =
        AdminDurationTypeResponseDTO(entity.id, entity.type, entity.active)


    override fun toEntity(dto: AdminDurationTypeNewRequestDTO, id: UUID): DurationType =
        DurationType(id, dto.type, dto.active)


    override fun toEntity(dto: AdminDurationTypeUpdateRequestDTO): DurationType =
        DurationType(dto.id, dto.type, dto.active)

}