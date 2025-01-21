package xenagos.application.service

import org.springframework.stereotype.Component
import xenagos.application.mapper.AdminTopicTagMapper
import xenagos.application.port.input.AdminTopicTagsUseCase
import xenagos.application.port.input.model.AdminTopicTagDto
import xenagos.application.port.output.AdminTopicTagsOutputPort

@Component
class AdminTopicTagsService(private val adminTopicTagsPersistence: AdminTopicTagsOutputPort) : AdminTopicTagsUseCase {

    override fun getAllTopicTags(): ArrayList<AdminTopicTagDto> {
        val mapper = AdminTopicTagMapper()
        val topicTagsDTO = arrayListOf<AdminTopicTagDto>()
        val topicTags = adminTopicTagsPersistence.getAllTopicTags()
        topicTags.forEach { topicTagsDTO.add(mapper.entityToDto(it)) }
        return topicTagsDTO
    }
}