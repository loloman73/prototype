package xenagos.domain.model

import java.util.*

data class DurationType(
    val id: UUID,
    val name: String,
    val active: Boolean
) : BaseDomainEntity