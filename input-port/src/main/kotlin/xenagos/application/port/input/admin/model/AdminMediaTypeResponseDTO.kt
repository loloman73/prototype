package xenagos.application.port.input.admin.model

import java.util.UUID

data class AdminMediaTypeResponseDTO(
    val id: UUID,
    val name: String,
    val active: Boolean
)