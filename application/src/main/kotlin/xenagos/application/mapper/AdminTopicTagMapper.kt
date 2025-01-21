package xenagos.application.mapper

import xenagos.application.port.input.model.AdminTopicTagDto
import xenagos.domain.model.TopicTag

class AdminTopicTagMapper {

    fun entityToDto(entity: TopicTag): AdminTopicTagDto {
        return AdminTopicTagDto(entity.id, entity.tag, entity.description)
    }

    fun dtoToEntity(dto: AdminTopicTagDto): TopicTag {
        return TopicTag(dto.topicTagId, dto.topicTag, dto.description)
    }
}