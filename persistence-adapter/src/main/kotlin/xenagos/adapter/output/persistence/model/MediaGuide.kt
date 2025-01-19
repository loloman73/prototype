package xenagos.adapter.output.persistence.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "media_guides")
open class MediaGuide {
    @Id
    @Column(name = "media_guide_id", nullable = false)
    open var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_point_id")
    open var tourPoint: TourPoint? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id")
    open var language: Language? = null

    @Column(name = "media_file_name", length = Integer.MAX_VALUE)
    open var mediaFileName: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_type_id")
    open var mediaType: MediaType? = null

    @Column(name = "duration")
    open var duration: Short? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "duration_type_id")
    open var durationType: DurationType? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amea_friendly_type_id")
    open var ameaFriendlyType: AmeaFriendlyType? = null

    @ManyToMany(mappedBy = "mediaGuides")
    open var ageRanges: MutableSet<AgeRange> = mutableSetOf()

    @ManyToMany(mappedBy = "mediaGuides")
    open var topicTags: MutableSet<TopicTag> = mutableSetOf()
}