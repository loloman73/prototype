package xenagos.adapter.output.persistence.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "accessibility_tags")
open class AccessibilityTagJpaEntity {
    @Id
    @Column(name = "accessibility_tag_id", nullable = false)
    open var id: UUID? = null

    @Column(name = "accessibility_tag", length = 35, nullable = false)
    open var accessibilityTag: String? = null

    @Column(name = "description", length = 250, nullable = false)
    open var description: String? = null

    @ManyToMany
    @JoinTable(
        name = "tour_point_accessibility_tag",
        joinColumns = [JoinColumn(name = "accessibility_tag_id")],
        inverseJoinColumns = [JoinColumn(name = "tour_point_id")]
    )
    open var tourPoints: MutableSet<TourPointJpaEntity> = mutableSetOf()
}