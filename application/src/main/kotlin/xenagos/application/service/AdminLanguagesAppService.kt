package xenagos.application.service

import org.springframework.stereotype.Service
import xenagos.application.mapper.toEntity
import xenagos.application.mapper.toResponseDto
import xenagos.application.port.input.AdminLanguagesUseCase
import xenagos.application.port.input.model.AdminLanguageEditRequestDTO
import xenagos.application.port.input.model.AdminLanguageNewRequestDTO
import xenagos.application.port.input.model.AdminLanguageResponseDTO
import xenagos.application.port.output.AdminLanguagesOutputPort
import java.util.*

@Service
class AdminLanguagesAppService(private val persistence: AdminLanguagesOutputPort) : AdminLanguagesUseCase {

    override fun getAllLanguages() = arrayListOf<AdminLanguageResponseDTO>().apply {
        persistence.getAllLanguages().forEach { add(it.toResponseDto()) }
    }

    override fun saveNewLanguage(requestDTO: AdminLanguageNewRequestDTO): AdminLanguageResponseDTO {
        val newEntityToSave = requestDTO.toEntity(UUID.randomUUID())
        val savedEntity = persistence.saveNewLanguage(newEntityToSave)
        return savedEntity.toResponseDto()
    }

    override fun updateLanguage(requestDTO: AdminLanguageEditRequestDTO): AdminLanguageResponseDTO {
        val entityToUpdate = requestDTO.toEntity()
        val updatedEntity = persistence.updateLanguage(entityToUpdate)
        return updatedEntity.toResponseDto()
    }

    override fun deleteLanguage(id: UUID) = persistence.deleteLanguage(id)
}