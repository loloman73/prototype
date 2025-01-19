package xenagos.adapter.output.persistence.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "age_ranges")
open class AgeRange {
    @Id
    @Column(name = "age_range_id", nullable = false)
    open var id: UUID? = null

    @Column(name = "age_range", length = Integer.MAX_VALUE)
    open var ageRange: String? = null

    @ManyToMany
    @JoinTable(
        name = "media_guide_age_range",
        joinColumns = [JoinColumn(name = "age_range_id")],
        inverseJoinColumns = [JoinColumn(name = "media_guide_id")]
    )
    open var mediaGuides: MutableSet<MediaGuide> = mutableSetOf()
}