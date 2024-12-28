package xenagos.application.domain.tour.entity

import xenagos.application.domain.tour.commun.*
import java.util.*

class MediaGuide(val id: MediaGuideUUID,
                 val language: Language,
                 val topicTags: Set<TopicTag>,
                 val ageRangeTags: Set<AgeRangeTag>,
                 val ameaFriendlyTag: AmeaFriendlyTag,
                 val mediaType: MediaType
) {
}

data class MediaGuideUUID(val value: UUID)
