package xenagos.application.port.input.admin.model

import java.util.*

data class AdminAgeGroupResponseDTO(
    val id: UUID,
    val entityName: String,
    val minAge: Byte,
    val maxAge: Byte,
    val active: Boolean
):BaseAdminResponseDTO