package xenagos.application.admin.service

import org.springframework.stereotype.Service
import xenagos.application.admin.mapper.toEntity
import xenagos.application.admin.mapper.toResponseDto
import xenagos.application.port.input.admin.AdminAgeGroupUseCase
import xenagos.application.port.input.admin.model.AdminAgeGroupEditRequestDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupResponseDTO
import xenagos.application.port.output.admin.AdminAgeGroupsOutputPort
import java.util.*

@Service
class AdminAgeGroupsAppService(private val persistence: AdminAgeGroupsOutputPort) : AdminAgeGroupUseCase {

    override fun getAllAgeGroups() = arrayListOf<AdminAgeGroupResponseDTO>().apply {
        persistence.getAllAgeGroups().forEach { add(it.toResponseDto()) }
    }

    override fun saveNewAgeGroup(requestDTO: AdminAgeGroupNewRequestDTO): AdminAgeGroupResponseDTO {
        val newEntityToSave = requestDTO.toEntity(UUID.randomUUID())
        val savedEntity = persistence.saveNewAgeGroup(newEntityToSave)
        return savedEntity.toResponseDto()
    }

    override fun updateAgeGroup(requestDTO: AdminAgeGroupEditRequestDTO): AdminAgeGroupResponseDTO {
        val entityToUpdate = requestDTO.toEntity()
        val updatedEntity = persistence.updateAgeGroup(entityToUpdate)
        return updatedEntity.toResponseDto()
    }

    override fun deleteAgeGroup(id: UUID) = persistence.deleteAgeGroup(id)
}