package xenagos.application.admin.mapper

import org.springframework.stereotype.Component
import xenagos.application.port.input.admin.model.AdminTopicTagUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminTopicTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminTopicTagResponseDTO
import xenagos.domain.model.TopicTag
import java.util.*

@Component
class AdminTopicTagMapper : BaseAdminMapper<
        TopicTag,
        AdminTopicTagNewRequestDTO,
        AdminTopicTagUpdateRequestDTO,
        AdminTopicTagResponseDTO> {

    override fun toResponseDto(entity: TopicTag): AdminTopicTagResponseDTO =
        AdminTopicTagResponseDTO(entity.id, entity.name, entity.description, entity.active)

    override fun toEntity(dto: AdminTopicTagNewRequestDTO, id: UUID ): TopicTag =
        TopicTag(id, dto.entityName, dto.description, dto.active)

    override fun toEntity(dto: AdminTopicTagUpdateRequestDTO): TopicTag =
        TopicTag(dto.id, dto.entityName, dto.description, dto.active)
}