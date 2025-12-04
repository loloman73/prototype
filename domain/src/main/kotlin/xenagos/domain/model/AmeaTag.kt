package xenagos.domain.model

import java.util.*

data class AmeaTag(
    val id: UUID,
    val name: String,
    val description: String,
    val active: Boolean
) : BaseDomainEntity

// Blind
// Deaf
// Autistic
