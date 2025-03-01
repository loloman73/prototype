package xenagos.application.port.input.model

import java.util.UUID

data class AdminTopicTagDto(
    val topicTagId: UUID,
    val topicTag: String,
    val description: String,
    val active: Boolean
)