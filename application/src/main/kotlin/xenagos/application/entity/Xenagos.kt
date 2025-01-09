package xenagos.application.entity

import java.util.*

class Xenagos(
    val id: XenagosUUID,
    val name: String,
    val photoFileName: String,
    val tours: List<TourUUID>
)

data class XenagosUUID(val value: UUID)