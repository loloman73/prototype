package xenagos.application.port.input.model

import java.util.UUID

data class AdminTopicTagDto(
    val topicTagId: UUID,
    val topicTag: String,
    var description: String
)