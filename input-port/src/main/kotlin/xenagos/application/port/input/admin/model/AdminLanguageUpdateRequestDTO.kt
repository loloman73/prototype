package xenagos.application.port.input.admin.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.*

data class AdminLanguageUpdateRequestDTO(
    @field:NotNull
    val id: UUID,

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

) : BaseAdminUpdateRequestDTO {
    companion object {
        fun createEmptyDeactivated() = AdminLanguageUpdateRequestDTO(UUID.randomUUID(), "", "", "", false)
    }
}