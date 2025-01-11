package xenagos.domain.model

import xenagos.domain.model.commun.Coordinates
import java.util.*

class TourPoint(
    val tourPointId: TourPointUUID,
    val title: String,
    val description: String,
    val coordinates: Coordinates,
    val mediaGuides: List<MediaGuide>
)


data class TourPointUUID(val value: UUID)