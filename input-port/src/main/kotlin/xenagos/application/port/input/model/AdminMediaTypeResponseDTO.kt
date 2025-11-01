package xenagos.application.port.input.model

import java.util.UUID

data class AdminMediaTypeResponseDTO(
    val id: UUID,
    val mediaType: String,
    val active: Boolean
)