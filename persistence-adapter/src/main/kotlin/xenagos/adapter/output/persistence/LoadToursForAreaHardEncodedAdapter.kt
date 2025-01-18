package xenagos.adapter.output.persistence

import org.springframework.stereotype.Service
import xenagos.common.model.Money
import xenagos.application.port.output.LoadToursForAreaOutPort
import xenagos.domain.model.*
import xenagos.domain.model.common.Coordinates
import xenagos.domain.model.common.DecimalDegreeLat
import xenagos.domain.model.common.DecimalDegreeLon
import java.util.*
import kotlin.random.Random
import kotlin.time.Duration

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
            id = UUID.randomUUID(),
            title = "",
            description = "",
            photoFileName = "",
            avgCoordinates = Coordinates(DecimalDegreeLat(0.0),DecimalDegreeLon(0.0)),
            totalRate = Random.nextInt(5).toByte(),
            totalReviews = Random.nextInt(100),
            price =Money(Random.nextInt(10), Currency.getInstance("EUR")) ,
            tourPoints = listOf(
                TourPoint(
                    id = UUID.randomUUID(),
                    title="AAA",
                    description = "aaaaaa",
                    coordinates = Coordinates(DecimalDegreeLat(0.0),DecimalDegreeLon(0.0)),
                    mediaGuides = listOf(MediaGuide(
                                        id = UUID.randomUUID(),
                                        language=Language(UUID.randomUUID(), "GR","Greek","Ελληνικά"),
                                        mediaFileName = "AccessAll",
                                        mediaType = MediaType(UUID.randomUUID(), ""),
                                        duration = Duration.parse("50"),
                                        durationType = DurationType(UUID.randomUUID(),""),
                                        ameaFriendlyTag = AmeaFriendlyTag(UUID.randomUUID(),"",""),
                                        topicTags = setOf(TopicTag(UUID.randomUUID(),"","")),
                                        ageRangeTags = setOf(AgeRangeTag(UUID.randomUUID(),"",""))
                                        )
                                    ),
                    accessibilityTags=setOf(AccessibilityTag(UUID.randomUUID(),"",""))
                )
            )
        )
    }

}