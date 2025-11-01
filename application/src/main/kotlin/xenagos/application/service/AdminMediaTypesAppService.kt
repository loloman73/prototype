package xenagos.application.service

import org.springframework.stereotype.Service
import xenagos.application.mapper.toEntity
import xenagos.application.mapper.toResponseDto
import xenagos.application.port.input.AdminMediaTypesUseCase
import xenagos.application.port.input.model.AdminMediaTypeEditRequestDTO
import xenagos.application.port.input.model.AdminMediaTypeNewRequestDTO
import xenagos.application.port.input.model.AdminMediaTypeResponseDTO
import xenagos.application.port.output.AdminMediaTypesOutputPort
import java.util.UUID

@Service
class AdminMediaTypesAppService(private val persistence: AdminMediaTypesOutputPort): AdminMediaTypesUseCase {

    override fun getAllMediaTypes() = arrayListOf<AdminMediaTypeResponseDTO>().apply {
        persistence.getAllMediaTypes().forEach { add(it.toResponseDto()) }
    }

    override fun saveNewMediaType(requestDTO: AdminMediaTypeNewRequestDTO): AdminMediaTypeResponseDTO {
        val newEntityToSave = requestDTO.toEntity(UUID.randomUUID())
        val savedEntity = persistence.saveNewMediaType(newEntityToSave)
        return savedEntity.toResponseDto()
    }

    override fun updateMediaType(requestDTO: AdminMediaTypeEditRequestDTO): AdminMediaTypeResponseDTO {
        val entityToUpdate = requestDTO.toEntity()
        val updatedEntity = persistence.updateMediaType(entityToUpdate)
        return updatedEntity.toResponseDto()
    }

    override fun deleteMediaType(id: UUID) = persistence.deleteMediaType(id)

}