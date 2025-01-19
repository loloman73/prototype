package xenagos.adapter.output.persistence.model

import jakarta.persistence.*

@Entity
@Table(name = "tour_point_accessibility_tag")
open class TourPointAccessibilityTag {
    @EmbeddedId
    open var id: TourPointAccessibilityTagId? = null

    @MapsId("tourPointId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_point_id", nullable = false)
    open var tourPoint: TourPoint? = null

    @MapsId("accessibilityTagId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "accessibility_tag_id", nullable = false)
    open var accessibilityTag: AccessibilityTag? = null
}