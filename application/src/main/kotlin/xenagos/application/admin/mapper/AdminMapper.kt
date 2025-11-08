package xenagos.application.admin.mapper

import java.util.UUID

interface AdminMapper<TEntity, TNew, TUpdate, TResponse> {
    fun toResponseDto(entity: TEntity): TResponse
    fun toEntity(dto: TNew, id: UUID): TEntity
    fun toEntity(dto: TUpdate): TEntity
}