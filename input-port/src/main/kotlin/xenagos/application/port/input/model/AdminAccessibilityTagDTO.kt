package xenagos.application.port.input.model

import java.util.*

data class AdminAccessibilityTagDTO(
    val id: UUID,
    val accessibilityTag: String,
    val description: String
)
