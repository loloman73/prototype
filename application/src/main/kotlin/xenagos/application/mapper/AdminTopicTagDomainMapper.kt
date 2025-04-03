package xenagos.application.mapper

import org.springframework.stereotype.Component
import xenagos.application.port.input.model.AdminTopicTagEditRequestDTO
import xenagos.application.port.input.model.AdminTopicTagNewRequestDTO
import xenagos.application.port.input.model.AdminTopicTagResponseDTO
import xenagos.domain.model.TopicTag
import java.util.*

@Component
class AdminTopicTagDomainMapper{

    fun entityToRespDto(entity: TopicTag): AdminTopicTagResponseDTO {
        return AdminTopicTagResponseDTO(entity.id, entity.name, entity.description, entity.active)
    }

    fun editReqDtoToEntity(dto: AdminTopicTagEditRequestDTO): TopicTag {
        return TopicTag(dto.id, dto.topicTag, dto.description, dto.active)
    }

    fun newReqDtoToEntity(dto: AdminTopicTagNewRequestDTO, id:UUID): TopicTag {
        return TopicTag(id, dto.topicTag, dto.description, dto.active)
    }

}