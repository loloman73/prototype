package xenagos.application.port.input.admin.model

import java.util.UUID

data class AdminDurationTypeResponseDTO(
    val id: UUID,
    val entityName: String,
    val active: Boolean
) : BaseAdminResponseDTO
