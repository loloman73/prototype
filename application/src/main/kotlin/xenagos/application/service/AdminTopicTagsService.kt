package xenagos.application.service

import org.springframework.stereotype.Service
import xenagos.application.mapper.AdminTopicTagMapper
import xenagos.application.port.input.AdminTopicTagsUseCase
import xenagos.application.port.input.model.AdminTopicTagDto
import xenagos.application.port.output.AdminTopicTagsOutputPort

@Service
class AdminTopicTagsService(private val adminTopicTagsPersistence: AdminTopicTagsOutputPort) : AdminTopicTagsUseCase {

    override fun getAllTopicTags(): ArrayList<AdminTopicTagDto> {
        val mapper = AdminTopicTagMapper()
        val adminTopicTagsDTO = arrayListOf<AdminTopicTagDto>()
        adminTopicTagsPersistence.getAllTopicTags().forEach { adminTopicTagsDTO.add(mapper.entityToDto(it)) }
        return adminTopicTagsDTO
    }
}