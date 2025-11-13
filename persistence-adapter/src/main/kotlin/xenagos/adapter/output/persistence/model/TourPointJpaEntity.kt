package xenagos.adapter.output.persistence.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "tour_points")
open class TourPointJpaEntity : BaseJPAEntity  {
    @Id
    @Column(name = "tour_point_id", nullable = false)
    open var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id")
    open var tour: TourJpaEntity? = null

    @Column(name = "title", length = Integer.MAX_VALUE)
    open var title: String? = null

    @Column(name = "description", length = Integer.MAX_VALUE)
    open var description: String? = null

    @Column(name = "coordinates", columnDefinition = "point")
    open var coordinates: Any? = null

    @OneToMany(mappedBy = "tourPoint")
    open var mediaGuides: MutableSet<MediaGuideJpaEntity> = mutableSetOf()

    @ManyToMany(mappedBy = "tourPoints")
    open var accessibilityTags: MutableSet<AccessibilityTagJpaEntity> = mutableSetOf()
}