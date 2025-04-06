package xenagos.application.service

import org.springframework.stereotype.Service
import xenagos.application.mapper.toEntity
import xenagos.application.mapper.toResponseDto
import xenagos.application.port.input.AdminAgeGroupUseCase
import xenagos.application.port.input.model.AdminAgeGroupEditRequestDTO
import xenagos.application.port.input.model.AdminAgeGroupNewRequestDTO
import xenagos.application.port.input.model.AdminAgeGroupResponseDTO
import xenagos.application.port.output.AdminAgeGroupsOutputPort
import java.util.*
import kotlin.collections.ArrayList

@Service
class AdminAgeGroupsAppService(private val persistence: AdminAgeGroupsOutputPort) : AdminAgeGroupUseCase {

    override fun getAllAgeGroups(): ArrayList<AdminAgeGroupResponseDTO> {
        val adminAgeGroupsDTO = arrayListOf<AdminAgeGroupResponseDTO>()
        persistence.getAllAgeGroups().forEach { adminAgeGroupsDTO.add(it.toResponseDto()) }
        return adminAgeGroupsDTO
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

    override fun deleteAgeGroup(id: UUID) {
        persistence.deleteAgeGroup(id)
    }
}