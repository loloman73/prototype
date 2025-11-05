package xenagos.application.admin.service

import org.springframework.stereotype.Service
import xenagos.application.admin.mapper.toEntity
import xenagos.application.admin.mapper.toResponseDto
import xenagos.application.port.input.admin.AdminMediaTypesUseCase
import xenagos.application.port.input.admin.model.AdminMediaTypeUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeNewRequestDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeResponseDTO
import xenagos.application.port.output.admin.AdminMediaTypesOutputPort
import java.util.UUID

@Service
class AdminMediaTypesAppService(private val persistence: AdminMediaTypesOutputPort): AdminMediaTypesUseCase {

    override fun getAll() = arrayListOf<AdminMediaTypeResponseDTO>().apply {
        persistence.getAllMediaTypes().forEach { add(it.toResponseDto()) }
    }

    override fun saveOneNew(requestDTO: AdminMediaTypeNewRequestDTO): AdminMediaTypeResponseDTO {
        val newEntityToSave = requestDTO.toEntity(UUID.randomUUID())
        val savedEntity = persistence.saveNewMediaType(newEntityToSave)
        return savedEntity.toResponseDto()
    }

    override fun updateOne(requestDTO: AdminMediaTypeUpdateRequestDTO): AdminMediaTypeResponseDTO {
        val entityToUpdate = requestDTO.toEntity()
        val updatedEntity = persistence.updateMediaType(entityToUpdate)
        return updatedEntity.toResponseDto()
    }

    override fun deleteOne(id: UUID) = persistence.deleteMediaType(id)

}