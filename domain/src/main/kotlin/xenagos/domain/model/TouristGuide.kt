package xenagos.domain.model

import java.util.*

class TouristGuide(
    val id: UUID,
    val name: String,
    val photoFileName: String,
    val tours: List<Tour>
) : BaseDomainEntity