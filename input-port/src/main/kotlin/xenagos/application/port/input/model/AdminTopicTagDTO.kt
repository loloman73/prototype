package xenagos.application.port.input.model

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID

data class AdminTopicTagDTO(

    val id: UUID,

    @NotNull
    @Size(max = 35)
    val topicTag: String,

    @NotNull
    @Size(max = 250)
    val description: String,

    @NotNull
    val active: Boolean
)