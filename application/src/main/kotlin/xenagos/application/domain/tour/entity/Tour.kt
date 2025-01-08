package xenagos.application.domain.tour.entity

import xenagos.commun.model.Money
import java.util.*

class Tour(
    val tourId: TourUUID,
    val title: String,
    val description: String,
    val price: Money,
    val photoFileName: String,
    val totalRate: Byte,
    val totalReviews: Int,
) {
}


data class TourUUID(val value: UUID)

