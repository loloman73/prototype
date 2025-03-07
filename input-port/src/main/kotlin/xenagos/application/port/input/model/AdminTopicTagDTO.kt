package xenagos.application.port.input.model

import java.util.UUID

data class AdminTopicTagDTO(
    val id: UUID,
    val topicTag: String,
    val description: String,
    val active: Boolean
)