package xenagos.application.port.input.model

import java.util.UUID

data class AdminAccessibilityTagResponseDTO(
    val id: UUID,
    val name: String,
    val description: String,
    val active: Boolean
)