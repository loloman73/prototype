package xenagos.domain.model

import xenagos.domain.model.common.Coordinates
import java.util.*

class TourPoint(
    val id: UUID,
    val title: String,
    val description: String,
    val coordinates: Coordinates,
    val mediaGuides: List<MediaGuide>,
    val accessibilityTags: Set<AccessibilityTag>
)