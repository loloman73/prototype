package xenagos.application.port.input.admin.model

import java.util.*

data class AdminLanguageResponseDTO(
    val id: UUID,
    val code: String,
    val englishName: String,
    val nativeName: String,
    val active: Boolean
):BaseAdminResponseDTO
