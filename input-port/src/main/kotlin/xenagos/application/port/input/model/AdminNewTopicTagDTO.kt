package xenagos.application.port.input.model

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class AdminNewTopicTagDTO(

    @field:NotEmpty
    @field:Size(max = 35)
    val topicTag: String = "",

    @field:NotEmpty
    @field:Size(max = 250)
    val description: String = "",

    //must have default false value because html POST ignores unchecked checkboxes
    val active: Boolean = false
)