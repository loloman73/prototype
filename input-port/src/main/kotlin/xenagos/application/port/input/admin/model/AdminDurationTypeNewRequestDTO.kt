package xenagos.application.port.input.admin.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class AdminDurationTypeNewRequestDTO(

    @field:NotBlank
    @field:Size(min = 3, max = 35)
    val entityName: String,

    @field:NotNull
    val active: Boolean = false

) : BaseAdminNewRequestDTO {
    companion object {
        fun createEmptyDeactivated() = AdminDurationTypeNewRequestDTO("")
    }
}
