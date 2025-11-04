package xenagos.application.admin.service

import org.springframework.stereotype.Service
import xenagos.application.admin.mapper.toEntity
import xenagos.application.admin.mapper.toResponseDto
import xenagos.application.port.input.admin.AdminAccessibilityTagsUseCase
import xenagos.application.port.input.admin.model.AdminAccessibilityTagUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminAccessibilityTagResponseDTO
import xenagos.application.port.input.admin.model.AdminAccessibilityTagNewRequestDTO
import xenagos.application.port.output.admin.AdminAccessibilityTagsOutputPort
import java.util.UUID

@Service
class AdminAccessibilityTagsAppService(private val persistence: AdminAccessibilityTagsOutputPort) :
    AdminAccessibilityTagsUseCase {

    override fun getAll() = arrayListOf<AdminAccessibilityTagResponseDTO>().apply {
        persistence.getAllAccessibilityTags().forEach { add(it.toResponseDto()) }
    }

    override fun saveOneNew(requestDTO: AdminAccessibilityTagNewRequestDTO): AdminAccessibilityTagResponseDTO {
        val newEntityToSave = requestDTO.toEntity(UUID.randomUUID())
        val savedEntity = persistence.saveNewAccessibilityTag(newEntityToSave)
        return savedEntity.toResponseDto()
    }

    override fun updateOne(requestDTO: AdminAccessibilityTagUpdateRequestDTO): AdminAccessibilityTagResponseDTO {
        val entityToUpdate = requestDTO.toEntity()
        val updatedEntity = persistence.updateAccessibilityTag(entityToUpdate)
        return updatedEntity.toResponseDto()
    }

    override fun deleteOne(id: UUID) = persistence.deleteAccessibilityTag(id)
}