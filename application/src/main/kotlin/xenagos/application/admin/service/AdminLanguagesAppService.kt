package xenagos.application.admin.service

import org.springframework.stereotype.Service
import xenagos.application.admin.mapper.toEntity
import xenagos.application.admin.mapper.toResponseDto
import xenagos.application.port.input.admin.AdminLanguagesUseCase
import xenagos.application.port.input.admin.model.AdminLanguageUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminLanguageNewRequestDTO
import xenagos.application.port.input.admin.model.AdminLanguageResponseDTO
import xenagos.application.port.output.admin.AdminLanguagesOutputPort
import java.util.UUID

@Service
class AdminLanguagesAppService(private val persistence: AdminLanguagesOutputPort): AdminLanguagesUseCase {

    override fun getAll() = arrayListOf<AdminLanguageResponseDTO>().apply {
        persistence.getAll().forEach { add(it.toResponseDto()) }
    }

    override fun saveOneNew(requestDTO: AdminLanguageNewRequestDTO): AdminLanguageResponseDTO {
        val newEntityToSave = requestDTO.toEntity(UUID.randomUUID())
        val savedEntity = persistence.saveOneNew(newEntityToSave)
        return savedEntity.toResponseDto()
    }

    override fun updateOne(requestDTO: AdminLanguageUpdateRequestDTO): AdminLanguageResponseDTO {
        val entityToUpdate = requestDTO.toEntity()
        val updatedEntity = persistence.updateOne(entityToUpdate)
        return updatedEntity.toResponseDto()
    }

    override fun deleteOne(id: UUID) = persistence.deleteOne(id)
}