package xenagos.application.admin.mapper

import xenagos.application.port.input.admin.model.BaseAdminNewRequestDTO
import xenagos.application.port.input.admin.model.BaseAdminResponseDTO
import xenagos.application.port.input.admin.model.BaseAdminUpdateRequestDTO
import java.util.UUID

interface BaseAdminMapper<
        TEntity,
        TNew: BaseAdminNewRequestDTO,
        TUpdate: BaseAdminUpdateRequestDTO,
        TResponse: BaseAdminResponseDTO> {
    fun toResponseDto(entity: TEntity): TResponse
    fun toEntity(dto: TNew, id: UUID): TEntity
    fun toEntity(dto: TUpdate): TEntity
}