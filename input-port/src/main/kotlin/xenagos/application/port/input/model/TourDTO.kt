package xenagos.application.port.input.model

import xenagos.common.model.Money
import java.util.*

data class TourDTO(
    val tourId: UUID,
    val xenagosId: UUID,
    val price: Money,
    val tourPointsQuantity: Short
)