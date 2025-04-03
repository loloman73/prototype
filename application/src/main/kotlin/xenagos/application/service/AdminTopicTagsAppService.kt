package xenagos.application.service

import org.springframework.stereotype.Service
import xenagos.application.mapper.AdminTopicTagDomainMapper
import xenagos.application.port.input.AdminTopicTagsUseCase
import xenagos.application.port.input.model.AdminTopicTagEditRequestDTO
import xenagos.application.port.input.model.AdminTopicTagNewRequestDTO
import xenagos.application.port.input.model.AdminTopicTagResponseDTO
import xenagos.application.port.output.AdminTopicTagsOutputPort
import java.util.*
import kotlin.collections.ArrayList

@Service
class AdminTopicTagsAppService(
    private val persistence: AdminTopicTagsOutputPort,
    private val mapper: AdminTopicTagDomainMapper
) : AdminTopicTagsUseCase {

    override fun getAllTopicTags(): ArrayList<AdminTopicTagResponseDTO> {
        val adminTopicTagsDTO = arrayListOf<AdminTopicTagResponseDTO>()
        persistence.getAllTopicTags().forEach { adminTopicTagsDTO.add(mapper.entityToRespDto(it)) }
        return adminTopicTagsDTO
    }

    override fun saveNewTopicTag(adminTopicTagNewRequestDTO: AdminTopicTagNewRequestDTO): AdminTopicTagResponseDTO {
        val newEntityToSave = mapper.newReqDtoToEntity(adminTopicTagNewRequestDTO, UUID.randomUUID())
        val savedEntity = persistence.saveNewTopicTag(newEntityToSave)
        return mapper.entityToRespDto(savedEntity)
    }

    override fun updateTopicTag(adminTopicTagEditRequestDTO: AdminTopicTagEditRequestDTO): AdminTopicTagResponseDTO {
        val entityToUpdate = mapper.editReqDtoToEntity(adminTopicTagEditRequestDTO)
        val updatedEntity = persistence.updateTopicTag(entityToUpdate)
        return mapper.entityToRespDto(updatedEntity)
    }

    override fun deleteTopicTag(topicTagId: UUID) {
        persistence.deleteTopicTag(topicTagId)
    }
}