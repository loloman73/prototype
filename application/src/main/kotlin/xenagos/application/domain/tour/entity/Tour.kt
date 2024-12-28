package xenagos.application.domain.tour.entity

import xenagos.application.commun.Money
import java.util.*

class Tour(val tourId: TourUUID,
           val title:String,
           val description:String,
           val price: Money,
           val photoFileName: String,
           val totalRate: Byte,
           val totalReviews: Int,
           val tourPoints: Set<TourPoint>
           ) {
}


data class TourUUID(val value: UUID)

