package xenagos.adapter.output.persistence.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "tour_points")
open class TourPoint {
    @Id
    @Column(name = "tour_point_id", nullable = false)
    open var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id")
    open var tour: Tour? = null

    @Column(name = "title", length = Integer.MAX_VALUE)
    open var title: String? = null

    @Column(name = "description", length = Integer.MAX_VALUE)
    open var description: String? = null

    @OneToMany(mappedBy = "tourPoint")
    open var mediaGuides: MutableSet<MediaGuide> = mutableSetOf()

    @ManyToMany(mappedBy = "tourPoints")
    open var accessibilityTags: MutableSet<AccessibilityTag> = mutableSetOf()

    /*
         TODO [Reverse Engineering] create field to map the 'coordinates' column
         Available actions: Define target Java type | Uncomment as is | Remove column mapping
            @Column(name = "coordinates", columnDefinition = "point")
            open var coordinates: Any? = null
        */
}