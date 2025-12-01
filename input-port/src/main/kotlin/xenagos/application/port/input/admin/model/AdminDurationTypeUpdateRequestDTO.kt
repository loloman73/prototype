package xenagos.application.port.input.admin.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID

data class AdminDurationTypeUpdateRequestDTO(

    @field:NotNull
    val id: UUID,

    @field:NotBlank
    @field:Size(min = 3, max = 35)
    val type: String,

    @field:NotNull
    val active: Boolean
):
    BaseAdminUpdateRequestDTO {
    companion object{
        fun createEmptyDeactivated() = AdminDurationTypeUpdateRequestDTO(UUID.randomUUID(), "", false)
    }
}
