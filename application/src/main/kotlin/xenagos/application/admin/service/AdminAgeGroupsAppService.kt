package xenagos.application.admin.service

import org.springframework.stereotype.Service
import xenagos.application.admin.mapper.toEntity
import xenagos.application.admin.mapper.toResponseDto
import xenagos.application.port.input.admin.AdminAgeGroupUseCase
import xenagos.application.port.input.admin.model.AdminAgeGroupUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupResponseDTO
import xenagos.application.port.output.admin.AdminAgeGroupsOutputPort
import java.util.UUID

@Service
class AdminAgeGroupsAppService(private val persistence: AdminAgeGroupsOutputPort): AdminAgeGroupUseCase {

    override fun getAll() = arrayListOf<AdminAgeGroupResponseDTO>().apply {
        persistence.getAll().forEach { add(it.toResponseDto()) }
    }

    override fun saveOneNew(requestDTO: AdminAgeGroupNewRequestDTO): AdminAgeGroupResponseDTO {
        val newEntityToSave = requestDTO.toEntity(UUID.randomUUID())
        val savedEntity = persistence.saveOneNew(newEntityToSave)
        return savedEntity.toResponseDto()
    }

    override fun updateOne(requestDTO: AdminAgeGroupUpdateRequestDTO): AdminAgeGroupResponseDTO {
        val entityToUpdate = requestDTO.toEntity()
        val updatedEntity = persistence.updateOne(entityToUpdate)
        return updatedEntity.toResponseDto()
    }

    override fun deleteOne(id: UUID) = persistence.deleteOne(id)
}