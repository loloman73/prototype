package xenagos.application.port.input.model

import java.util.*

data class AdminAccessibilityTagDTO(
    val accessibilityTagId: UUID,
    val accessibilityTag: String,
    val description: String
)
