package xenagos.application.port.input.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size


data class AdminAgeGroupNewRequestDTO(

    @field:NotBlank
    @field:Size(min = 3, max = 35)
    val groupName: String,

    @field:NotNull
    @field:Positive
    val minAge: Byte,

    @field:NotNull
    @field:Positive
    val maxAge: Byte,

    // needs default false value because html POST calls ignore checkboxes when they are unchecked
    // ** Leaked dependency on implementation **
    // TODO: find solution to send unchecked checkbox status with POST calls
    @field:NotNull
    val active: Boolean = false
)