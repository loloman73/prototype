package xenagos.domain.model

import xenagos.common.model.Coordinates
import java.util.*

class TourPoint(
    val id: UUID,
    val title: String,
    val description: String,
    val coordinates: Coordinates,
    val mediaGuides: List<MediaGuide>,
    val accessibilityTags: Set<AccessibilityTag>
) : BaseDomainEntity