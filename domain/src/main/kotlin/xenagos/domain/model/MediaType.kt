package xenagos.domain.model

import java.util.*

class MediaType(
    val id: UUID,
    val name: String,
    val active: Boolean
) : BaseDomainEntity

//    audioGuide,
//    visualGuide
