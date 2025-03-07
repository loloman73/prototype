package xenagos.application.port.input.model

import java.util.*

data class AdminAgeGroupDTO(
    val id: UUID,
    val ageGroup: String,
    val minAge: Byte,
    val maxAge: Byte
)