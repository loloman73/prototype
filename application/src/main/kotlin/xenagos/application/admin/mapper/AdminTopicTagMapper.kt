package xenagos.application.admin.mapper

import xenagos.application.port.input.admin.model.AdminTopicTagEditRequestDTO
import xenagos.application.port.input.admin.model.AdminTopicTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminTopicTagResponseDTO
import xenagos.domain.model.TopicTag
import java.util.*

fun TopicTag.toResponseDto() =
    AdminTopicTagResponseDTO(this.id, this.tagName, this.description, this.active)

fun AdminTopicTagNewRequestDTO.toEntity(id: UUID) =
    TopicTag(id, this.name, this.description, this.active)

fun AdminTopicTagEditRequestDTO.toEntity() =
    TopicTag(this.id, this.name, this.description, this.active)
