package xenagos.adapter.output.persistence

import org.springframework.stereotype.Service
import xenagos.common.model.Money
import xenagos.application.port.output.LoadToursForAreaOutPort
import xenagos.common.model.Coordinates
import xenagos.common.model.DecimalDegreeLat
import xenagos.common.model.DecimalDegreeLon
import xenagos.domain.model.*
import java.util.*
import kotlin.random.Random

@Service
class LoadToursForAreaHardEncodedAdapter : LoadToursForAreaOutPort {
    override fun whereAreaIs(area: Int): Set<Tour>? {
        return if (area == 51) {
            setOf(fakeTour(), fakeTour(), fakeTour(), fakeTour(), fakeTour(), fakeTour(), fakeTour(), fakeTour())
            // setOf(fakeTour(), fakeTour())
        } else null
    }

    private fun fakeTour(): Tour {
        return Tour(
            id = UUID.randomUUID(),
            title = "Tour Title",
            description = "Tour Description",
            photoFileName = UUID.randomUUID(),
            avgCoordinates = Coordinates(DecimalDegreeLat(0.0), DecimalDegreeLon(0.0)),
            totalRate = Random.nextInt(5).toByte(),
            totalReviews = Random.nextInt(100),
            price = Money(Random.nextInt(10), Currency.getInstance("EUR")),
            tourPoints = listOf(
                TourPoint(
                    id = UUID.randomUUID(),
                    title = "Tour Point title",
                    description = "Tour Point description",
                    coordinates = Coordinates(DecimalDegreeLat(0.0), DecimalDegreeLon(0.0)),
                    mediaGuides = listOf(
                        MediaGuide(
                            id = UUID.randomUUID(),
                            language = Language(UUID.randomUUID(), "GR", "Greek", "Ελληνικά", true),
                            mediaFileName = "",
                            mediaType = MediaType(UUID.randomUUID(), "",  true),
                            duration = Random.nextInt(1, 240).toShort(),
                            durationType = DurationType(UUID.randomUUID(), "aaa", false),
                            ameaFriendlyTag = AmeaFriendlyTag(UUID.randomUUID(), "", ""),
                            topicTags = setOf(TopicTag(UUID.randomUUID(), "", "", true)),
                            ageGroups = setOf(AgeGroup(UUID.randomUUID(), "aaa", 12, 19, true))
                        )
                    ),
                    accessibilityTags = setOf(AccessibilityTag(UUID.randomUUID(), "", "", true))
                )
            )
        )
    }

}