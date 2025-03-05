package xenagos.application.port.input.model

import xenagos.common.model.Coordinates
import xenagos.common.model.Money
import java.util.*

data class TourDTO(
    val tourId: UUID,
    val title: String,
    val description: String,
    val price: Money,
    val tourPhotoFileName: UUID,
    val tourRate: Byte?,
    val tourReviews: Int?,
    val avgCoordinates: Coordinates,

    val xenagosId: UUID,
    val xenagosName: String,
    val xenagosPhotoFileName: UUID,
    val tourPointsQuantity: Short
)