package xenagos.domain.model

import java.util.*

class Xenagos(
    val id: UUID,
    val name: String,
    val photoFileName: String,
    val tours: List<Tour>
) : BaseDomainEntity