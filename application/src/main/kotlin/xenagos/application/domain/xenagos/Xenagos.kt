package xenagos.application.domain.xenagos

import xenagos.application.domain.tour.entity.TourUUID
import java.util.*

class Xenagos(
    val id: XenagosUUID,
    val name: String,
    val photoFileName: String,
    val tours: List<TourUUID>
)

data class XenagosUUID(val value: UUID)