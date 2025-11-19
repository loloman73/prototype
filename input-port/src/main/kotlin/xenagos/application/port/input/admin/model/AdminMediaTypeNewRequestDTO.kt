package xenagos.application.port.input.admin.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class AdminMediaTypeNewRequestDTO(

    @field:NotBlank
    @field:Size(min = 3, max = 35)
    val name: String,

    @field:NotNull
    val active: Boolean

) : BaseAdminNewRequestDTO {
    companion object {
        fun createEmptyDeactivated() = AdminMediaTypeNewRequestDTO("", false)
    }
}