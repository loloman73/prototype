package xenagos.adapter.output.persistence.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "age_groups")
open class AgeGroupJpaEntity {
    @Id
    @Column(name = "age_group_id", nullable = false)
    open var id: UUID? = null

    @Column(name = "age_group", nullable = false, length = 20)
    open var ageGroup: String? = null

    @Column(name = "min_age", nullable = false)
    open var minAge: Byte? = null

    @Column(name = "max_age", nullable = false)
    open var maxAge: Byte? = null

    @ManyToMany
    @JoinTable(
        name = "media_guide_age_group",
        joinColumns = [JoinColumn(name = "age_group_id")],
        inverseJoinColumns = [JoinColumn(name = "media_guide_id")]
    )
    open var mediaGuides: MutableSet<MediaGuideJpaEntity> = mutableSetOf()
}