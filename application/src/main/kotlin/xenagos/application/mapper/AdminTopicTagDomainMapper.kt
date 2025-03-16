package xenagos.application.mapper

import org.springframework.stereotype.Component
import xenagos.application.port.input.model.AdminNewTopicTagDTO
import xenagos.application.port.input.model.AdminTopicTagDTO
import xenagos.domain.model.TopicTag
import java.util.*

@Component
class AdminTopicTagDomainMapper {

    fun entityToDto(entity: TopicTag): AdminTopicTagDTO {
        return AdminTopicTagDTO(entity.id, entity.name, entity.description, entity.active)
    }

    fun dtoToEntity(dto: AdminTopicTagDTO): TopicTag {
        return TopicTag(dto.id, dto.topicTag, dto.description, dto.active)
    }

    fun newDtoToEntity(dto: AdminNewTopicTagDTO): TopicTag {
        return TopicTag(UUID.randomUUID(), dto.topicTag, dto.description, dto.active)
    }

}