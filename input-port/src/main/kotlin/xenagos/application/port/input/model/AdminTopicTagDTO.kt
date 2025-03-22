package xenagos.application.port.input.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID

data class AdminTopicTagDTO (

    @field:NotNull
    val id: UUID,

    @field:NotBlank
    @field:Size(max = 35)
    val topicTag: String,

    @field:NotBlank
    @field:Size(max = 250)
    val description: String,

    @field:NotNull
    val active: Boolean
)