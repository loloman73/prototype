package xenagos.domain.model

import java.util.*

class MediaGuide(
    val id: UUID,
    val language: Language,
    val mediaFileName: String,
    val mediaType: MediaType,
    val duration: Short,
    val durationType: DurationType,
    val ameaTag: AmeaTag,
    val topicTags: Set<TopicTag>,
    val ageGroups: Set<AgeGroup>
) : BaseDomainEntity