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

    @field:NotNull
    val active: Boolean
)