package xenagos.application.admin.service

import org.springframework.stereotype.Service
import xenagos.application.admin.mapper.toEntity
import xenagos.application.admin.mapper.toResponseDto
import xenagos.application.port.input.admin.AdminTopicTagsUseCase
import xenagos.application.port.input.admin.model.AdminTopicTagEditRequestDTO
import xenagos.application.port.input.admin.model.AdminTopicTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminTopicTagResponseDTO
import xenagos.application.port.output.admin.AdminTopicTagsOutputPort
import java.util.*

@Service
class AdminTopicTagsAppService(private val persistence: AdminTopicTagsOutputPort) : AdminTopicTagsUseCase {

    override fun getAllTopicTags() = arrayListOf<AdminTopicTagResponseDTO>().apply {
        persistence.getAllTopicTags().forEach { add(it.toResponseDto()) }
    }

    override fun saveNewTopicTag(requestDTO: AdminTopicTagNewRequestDTO): AdminTopicTagResponseDTO {
        val newEntityToSave = requestDTO.toEntity(UUID.randomUUID())
        val savedEntity = persistence.saveNewTopicTag(newEntityToSave)
        return savedEntity.toResponseDto()
    }

    override fun updateTopicTag(requestDTO: AdminTopicTagEditRequestDTO): AdminTopicTagResponseDTO {
        val entityToUpdate = requestDTO.toEntity()
        val updatedEntity = persistence.updateTopicTag(entityToUpdate)
        return updatedEntity.toResponseDto()
    }

    override fun deleteTopicTag(id: UUID) = persistence.deleteTopicTag(id)
}