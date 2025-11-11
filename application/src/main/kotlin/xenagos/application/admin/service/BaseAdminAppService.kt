package xenagos.application.admin.service

import xenagos.application.admin.mapper.BaseAdminMapper
import xenagos.application.port.input.admin.BaseAdminUseCase
import xenagos.application.port.input.admin.model.BaseAdminNewRequestDTO
import xenagos.application.port.input.admin.model.BaseAdminResponseDTO
import xenagos.application.port.input.admin.model.BaseAdminUpdateRequestDTO
import xenagos.application.port.output.admin.BaseAdminOutputPort
import java.util.UUID

abstract class BaseAdminAppService<
        TEntity,
        TNew : BaseAdminNewRequestDTO,
        TUpdate : BaseAdminUpdateRequestDTO,
        TResponse : BaseAdminResponseDTO>
    (private val persistence: BaseAdminOutputPort<TEntity>,
     private val mapper: BaseAdminMapper<TEntity, TNew, TUpdate, TResponse>) :
    BaseAdminUseCase<TNew, TUpdate, TResponse> {

    override fun getAll(): ArrayList<TResponse> = arrayListOf<TResponse>().apply {
        persistence.getAll().forEach { add(mapper.toResponseDto(it)) }
    }

    override fun saveOneNew(requestDTO: TNew): TResponse {
        val newEntityToSave = mapper.toEntity(requestDTO, UUID.randomUUID())
        val savedEntity = persistence.saveOneNew(newEntityToSave)
        return mapper.toResponseDto(savedEntity)
    }

    override fun updateOne(requestDTO: TUpdate): TResponse {
        val entityToUpdate = mapper.toEntity(requestDTO)
        val updatedEntity = persistence.updateOne(entityToUpdate)
        return mapper.toResponseDto(updatedEntity)
    }

    override fun deleteOne(id: UUID) = persistence.deleteOne(id)
}