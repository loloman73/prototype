package xenagos.adapter.output.persistence.model

import jakarta.persistence.*

@Entity
@Table(name = "media_guide_age_range")
open class MediaGuideAgeRange {
    @EmbeddedId
    open var id: MediaGuideAgeRangeId? = null

    @MapsId("mediaGuideId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "media_guide_id", nullable = false)
    open var mediaGuide: MediaGuide? = null

    @MapsId("ageRangeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "age_range_id", nullable = false)
    open var ageRange: AgeRange? = null
}