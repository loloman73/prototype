package xenagos.adapter.output.persistence

import org.springframework.stereotype.Service
import xenagos.domain.model.Tour
import xenagos.domain.model.TourUUID
import xenagos.commun.model.Money
import xenagos.application.port.output.LoadToursForAreaOutPort
import java.util.*
import kotlin.random.Random

@Service
class LoadToursForAreaHardEncodedAdapter : LoadToursForAreaOutPort {
    override fun whereAreaIs(area: Int): Set<Tour>? {
        return if (area == 51) {
            setOf(fakeTour(), fakeTour(), fakeTour(), fakeTour(), fakeTour(), fakeTour(),fakeTour(),fakeTour())
           // setOf(fakeTour(), fakeTour())
        } else null
    }

    private fun fakeTour(): Tour {
        return Tour(
            tourId = TourUUID(UUID.randomUUID()),
            title = "",
            description = "",
            photoFileName = "",
            totalRate = Random.nextInt(5).toByte(),
            totalReviews = Random.nextInt(100),
            price = Money(Random.nextInt(10), Currency.getInstance("EUR")),
        )
    }

}