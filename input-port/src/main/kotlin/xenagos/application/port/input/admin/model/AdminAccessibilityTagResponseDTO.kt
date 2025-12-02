package xenagos.application.port.input.admin.model

import java.util.UUID

data class AdminAccessibilityTagResponseDTO(
    val id: UUID,
    val entityName: String,
    val description: String,
    val active: Boolean
) : BaseAdminResponseDTO