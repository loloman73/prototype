package xenagos.application.service

import org.springframework.stereotype.Service
import xenagos.application.mapper.toEntity
import xenagos.application.mapper.toResponseDto
import xenagos.application.port.input.AdminTopicTagsUseCase
import xenagos.application.port.input.model.AdminTopicTagEditRequestDTO
import xenagos.application.port.input.model.AdminTopicTagNewRequestDTO
import xenagos.application.port.input.model.AdminTopicTagResponseDTO
import xenagos.application.port.output.AdminTopicTagsOutputPort
import java.util.*
import kotlin.collections.ArrayList

@Service
class AdminTopicTagsAppService(private val persistence: AdminTopicTagsOutputPort) : AdminTopicTagsUseCase {

    override fun getAllTopicTags(): ArrayList<AdminTopicTagResponseDTO> {
        val adminTopicTagsDTO = arrayListOf<AdminTopicTagResponseDTO>()
        persistence.getAllTopicTags().forEach { adminTopicTagsDTO.add(it.toResponseDto()) }
        return adminTopicTagsDTO
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

    override fun deleteTopicTag(id: UUID) {
        persistence.deleteTopicTag(id)
    }
}