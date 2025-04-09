package xenagos.application.port.input.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.*

data class AdminLanguageEditRequestDTO(
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

    // needs default false value because html POST calls ignore checkboxes when they are unchecked
    // ** Leaked dependency on implementation **
    // TODO: find solution to send unchecked checkbox status with POST calls
    @field:NotNull
    val active: Boolean = false
)
