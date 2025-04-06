package xenagos.application.port.input.model

import java.util.*

data class AdminAgeGroupResponseDTO(
    val id: UUID,
    val groupName: String,
    val minAge: Byte,
    val maxAge: Byte,
    val active: Boolean
)