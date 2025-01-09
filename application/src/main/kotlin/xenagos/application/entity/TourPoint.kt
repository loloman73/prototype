package xenagos.application.entity

import xenagos.application.entity.commun.Coordinates
import java.util.*

class TourPoint(
    val tourPointId: TourPointUUID,
    val title: String,
    val description: String,
    val coordinates: Coordinates,
    val mediaGuides: List<MediaGuide>
)


data class TourPointUUID(val value: UUID)