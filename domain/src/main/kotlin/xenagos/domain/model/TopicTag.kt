package xenagos.domain.model

import java.util.*

class TopicTag(
    val id: UUID,
    val tagName: String,
    val description: String,
    val active: Boolean
) : BaseDomainEntity

//    Architecture
//    Religion
//    Military
//    Arts
//    Technology
