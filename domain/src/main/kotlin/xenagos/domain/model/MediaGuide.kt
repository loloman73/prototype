package xenagos.domain.model

import java.util.*

class MediaGuide(
    val id: UUID,
    val language: Language,
    val mediaFileName: String,
    val mediaType: MediaType,
    val duration: Short,
    val durationType: DurationType,
    val ameaFriendlyTag: AmeaFriendlyTag,
    val topicTags: Set<TopicTag>,
    val ageRangeTags: Set<AgeRangeTag>
)