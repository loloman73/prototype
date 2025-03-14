package xenagos.application.port.input.model

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID

data class AdminTopicTagDTO (

    val id: UUID,

    @field:NotEmpty
    @field:Size(max = 35)
    val topicTag: String,

    @field:NotEmpty
    @field:Size(max = 250)
    val description: String,

    @field:NotNull
    val active: Boolean
)