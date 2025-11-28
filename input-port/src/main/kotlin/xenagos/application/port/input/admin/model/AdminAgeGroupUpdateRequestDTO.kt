package xenagos.application.port.input.admin.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.util.*

data class AdminAgeGroupUpdateRequestDTO(

    @field:NotNull
    val id: UUID,

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
    val active: Boolean = false

) : BaseAdminUpdateRequestDTO {
    companion object {
        fun createEmptyDeactivated() = AdminAgeGroupUpdateRequestDTO(UUID.randomUUID(), "", 0, 0)
    }
}