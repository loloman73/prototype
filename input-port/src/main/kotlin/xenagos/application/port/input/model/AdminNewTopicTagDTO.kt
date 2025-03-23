package xenagos.application.port.input.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class AdminNewTopicTagDTO(

    @field:NotBlank
    @field:Size(max = 35)
    val topicTag: String,

    @field:NotBlank
    @field:Size(max = 250)
    val description: String,

    // needs default false value because html POST calls ignore checkboxes when they are unchecked
    // ** Leaked dependency on implementation **
    // TODO: find solution to send unchecked checkbox status with POST calls
    @field:NotNull
    val active: Boolean = false
)