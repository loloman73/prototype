package xenagos.domain.model

import java.util.*

class MediaType(
    val id: UUID,
    val type: String,
    val active: Boolean
) : BaseDomainEntity

//    audioGuide,
//    visualGuide
