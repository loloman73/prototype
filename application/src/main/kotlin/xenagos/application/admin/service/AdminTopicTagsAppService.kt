package xenagos.application.admin.service

import org.springframework.stereotype.Service
import xenagos.application.admin.mapper.toEntity
import xenagos.application.admin.mapper.toResponseDto
import xenagos.application.port.input.admin.AdminTopicTagsUseCase
import xenagos.application.port.input.admin.model.AdminTopicTagUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminTopicTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminTopicTagResponseDTO
import xenagos.application.port.output.admin.AdminTopicTagsOutputPort
import java.util.*

@Service
class AdminTopicTagsAppService(private val persistence: AdminTopicTagsOutputPort) : AdminTopicTagsUseCase {

    override fun getAll() = arrayListOf<AdminTopicTagResponseDTO>().apply {
        persistence.getAllTopicTags().forEach { add(it.toResponseDto()) }
    }

    override fun saveOneNew(requestDTO: AdminTopicTagNewRequestDTO): AdminTopicTagResponseDTO {
        val newEntityToSave = requestDTO.toEntity(UUID.randomUUID())
        val savedEntity = persistence.saveNewTopicTag(newEntityToSave)
        return savedEntity.toResponseDto()
    }

    override fun updateOne(requestDTO: AdminTopicTagUpdateRequestDTO): AdminTopicTagResponseDTO {
        val entityToUpdate = requestDTO.toEntity()
        val updatedEntity = persistence.updateTopicTag(entityToUpdate)
        return updatedEntity.toResponseDto()
    }

    override fun deleteOne(id: UUID) = persistence.deleteTopicTag(id)
}