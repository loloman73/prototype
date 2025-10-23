package xenagos.application.mapper

import xenagos.application.port.input.model.AdminTopicTagEditRequestDTO
import xenagos.application.port.input.model.AdminTopicTagNewRequestDTO
import xenagos.application.port.input.model.AdminTopicTagResponseDTO
import xenagos.domain.model.TopicTag
import java.util.*

fun TopicTag.toResponseDto() =
    AdminTopicTagResponseDTO(this.id, this.tagName, this.description, this.active)

fun AdminTopicTagNewRequestDTO.toEntity(id: UUID) =
    TopicTag(id, this.name, this.description, this.active)

fun AdminTopicTagEditRequestDTO.toEntity() =
    TopicTag(this.id, this.name, this.description, this.active)
