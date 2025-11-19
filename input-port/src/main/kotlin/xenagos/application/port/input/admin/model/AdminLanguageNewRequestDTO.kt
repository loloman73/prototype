package xenagos.application.port.input.admin.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class AdminLanguageNewRequestDTO(
    @field:NotBlank
    @field:Size(min = 3, max = 3)
    val code: String,

    @field:NotBlank
    @field:Size(min = 3, max = 35)
    val englishName: String,

    @field:NotBlank
    @field:Size(min = 3, max = 35)
    val nativeName: String,

    @field:NotNull
    val active: Boolean

) : BaseAdminNewRequestDTO {
    companion object {
        fun createEmptyDeactivated() = AdminLanguageNewRequestDTO("", "", "", false)
    }
}