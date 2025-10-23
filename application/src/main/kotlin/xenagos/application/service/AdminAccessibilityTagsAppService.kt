package xenagos.application.service

import org.springframework.stereotype.Service
import xenagos.application.mapper.toEntity
import xenagos.application.mapper.toResponseDto
import xenagos.application.port.input.AdminAccessibilityTagsUseCase
import xenagos.application.port.input.model.AdminAccessibilityTagEditRequestDTO
import xenagos.application.port.input.model.AdminAccessibilityTagResponseDTO
import xenagos.application.port.input.model.AdminAccessibilityTagNewRequestDTO
import xenagos.application.port.output.AdminAccessibilityTagsOutputPort
import java.util.*

@Service
class AdminAccessibilityTagsAppService(private val persistence: AdminAccessibilityTagsOutputPort) :
    AdminAccessibilityTagsUseCase {

    override fun getAllAccessibilityTags() = arrayListOf<AdminAccessibilityTagResponseDTO>().apply {
        persistence.getAllAccessibilityTags().forEach { add(it.toResponseDto()) }
    }

    override fun saveNewAccessibilityTag(requestDTO: AdminAccessibilityTagNewRequestDTO): AdminAccessibilityTagResponseDTO {
        val newEntityToSave = requestDTO.toEntity(UUID.randomUUID())
        val savedEntity = persistence.saveNewAccessibilityTag(newEntityToSave)
        return savedEntity.toResponseDto()
    }

    override fun updateAccessibilityTag(requestDTO: AdminAccessibilityTagEditRequestDTO): AdminAccessibilityTagResponseDTO {
        val entityToUpdate = requestDTO.toEntity()
        val updatedEntity = persistence.updateAccessibilityTag(entityToUpdate)
        return updatedEntity.toResponseDto()
    }

    override fun deleteAccessibilityTag(id: UUID) = persistence.deleteAccessibilityTag(id)
}