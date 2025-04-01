package xenagos.application.mapper

import org.springframework.stereotype.Component
import xenagos.application.port.input.model.AdminTopicTagEditRequestDTO
import xenagos.application.port.input.model.AdminTopicTagNewRequestDTO
import xenagos.application.port.input.model.AdminTopicTagResponseDTO
import xenagos.domain.model.TopicTag
import java.util.*

@Component
class AdminTopicTagDomainMapper {

    fun entityToDto(entity: TopicTag): AdminTopicTagResponseDTO {
        return AdminTopicTagResponseDTO(entity.id, entity.name, entity.description, entity.active)
    }

    fun dtoToEntity(dto: AdminTopicTagResponseDTO): TopicTag {
        return TopicTag(dto.id, dto.topicTag, dto.description, dto.active)
    }

    fun editDtoToEntity(dto: AdminTopicTagEditRequestDTO): TopicTag {
        return TopicTag(dto.id, dto.topicTag, dto.description, dto.active)
    }

    fun newDtoToEntity(dto: AdminTopicTagNewRequestDTO, id:UUID): TopicTag {
        return TopicTag(id, dto.topicTag, dto.description, dto.active)
    }

}