package xenagos.domain.model

import xenagos.domain.model.commun.*
import java.util.*
import kotlin.time.Duration

class MediaGuide(
    val id: MediaGuideUUID,
    val language: Language,
    val mediaFileName: String,
    val mediaType: MediaType,
    val duration: Duration,
    val durationType: DurationType,
    val ameaFriendlyTag: AmeaFriendlyTag,
    val topicTags: Set<TopicTag>,
    val ageRangeTags: Set<AgeRangeTag>
) {
}

data class MediaGuideUUID(val value: UUID)
