package xenagos.application.domain.tour.entity

import xenagos.application.domain.tour.commun.Coordinates
import java.util.*

class TourPoint (val tourPointId: TourPointUUID,
                 val title:String,
                 val description:String,
                 val coordinates: Coordinates,
                 val mediaGuides: Set<MediaGuide>)


data class TourPointUUID(val value: UUID)