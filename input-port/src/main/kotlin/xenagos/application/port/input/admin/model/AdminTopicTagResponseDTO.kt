package xenagos.application.port.input.admin.model

import java.util.UUID

data class AdminTopicTagResponseDTO(
    val id: UUID,
    val entityName: String,
    val description: String,
    val active: Boolean
):BaseAdminResponseDTO