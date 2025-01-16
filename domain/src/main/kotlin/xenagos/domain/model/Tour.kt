package xenagos.domain.model

import xenagos.common.model.Money
import xenagos.domain.model.common.Coordinates
import java.util.*

class Tour(
    val id: UUID,
    val title: String,
    val description: String,
    val price: Money,
    val photoFileName: String,
    val totalRate: Byte?,
    val totalReviews: Int?,
    val avgCoordinates: Coordinates,
    val tourPoints: List<TourPoint>
)