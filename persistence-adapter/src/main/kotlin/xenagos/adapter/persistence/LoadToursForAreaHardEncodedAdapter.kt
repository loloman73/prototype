package xenagos.adapter.persistence

import org.springframework.stereotype.Service
import xenagos.commun.model.Money
import xenagos.application.domain.tour.entity.*
import xenagos.application.domain.xenagos.XenagosUUID
import xenagos.application.port.output.LoadToursForAreaOutPort
import java.util.*

@Service
class LoadToursForAreaHardEncodedAdapter : LoadToursForAreaOutPort {
    override fun whereAreaIs(area: Int): Set<Tour>? {
        if (area == 51) {
            val x1 = Tour(
                tourId = TourUUID(UUID.randomUUID()),
                title = "",
                description = "",
                photoFileName = "",
                totalRate = 0,
                totalReviews = 0,
                price = Money(12, Currency.getInstance("EUR")),
            )

            val x2 = Tour(
                tourId = TourUUID(UUID.randomUUID()),
                title = "",
                description = "",
                photoFileName = "",
                totalRate = 0,
                totalReviews = 0,
                price = Money(23, Currency.getInstance("EUR")),
            )

            val x3 = Tour(
                tourId = TourUUID(UUID.randomUUID()),
                title = "",
                description = "",
                photoFileName = "",
                totalRate = 0,
                totalReviews = 0,
                price = Money(49, Currency.getInstance("EUR")),
            )

            return setOf(x1, x2, x3)
        } else return null
    }
}