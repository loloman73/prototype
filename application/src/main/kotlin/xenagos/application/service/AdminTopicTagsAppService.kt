package xenagos.application.service

import org.springframework.stereotype.Service
import xenagos.application.mapper.AdminTopicTagDomainMapper
import xenagos.application.port.input.AdminTopicTagsUseCase
import xenagos.application.port.input.model.AdminTopicTagNewDTO
import xenagos.application.port.input.model.AdminTopicTagDTO
import xenagos.application.port.output.AdminTopicTagsOutputPort
import java.util.*
import kotlin.collections.ArrayList

@Service
class AdminTopicTagsAppService(
    private val persistence: AdminTopicTagsOutputPort,
    private val mapper: AdminTopicTagDomainMapper
) : AdminTopicTagsUseCase {

    override fun getAllTopicTags(): ArrayList<AdminTopicTagDTO> {
        val adminTopicTagsDTO = arrayListOf<AdminTopicTagDTO>()
        persistence.getAllTopicTags().forEach { adminTopicTagsDTO.add(mapper.entityToDto(it)) }
        return adminTopicTagsDTO
    }

    override fun saveNewTopicTag(adminNewTopicTagDTO: AdminTopicTagNewDTO): AdminTopicTagDTO {
        val savedEntity = persistence.saveNewTopicTag(mapper.newDtoToEntity(adminNewTopicTagDTO, UUID.randomUUID()))
        return mapper.entityToDto(savedEntity)
    }

    override fun updateTopicTag(adminTopicTagDTO: AdminTopicTagDTO): AdminTopicTagDTO {
        val updatedEntity = persistence.updateTopicTag(mapper.dtoToEntity(adminTopicTagDTO))
        return mapper.entityToDto(updatedEntity)
    }

    override fun deleteTopicTag(adminTopicTagDtoId: UUID) {
        persistence.deleteTopicTag(adminTopicTagDtoId)
    }
}