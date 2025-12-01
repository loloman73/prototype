package xenagos.adapter.output.persistence.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "duration_types")
open class DurationTypeJpaEntity : BaseJPAEntity  {
    @Id
    @Column(name = "duration_type_id", nullable = false)
    open var id: UUID? = null

    @Column(name = "duration_type", length = 35, nullable = false)
    open var durationType: String? = null

    @Column(name = "active", nullable = false)
    open var active: Boolean? = null

    @OneToMany(mappedBy = "durationType")
    open var mediaGuides: MutableSet<MediaGuideJpaEntity> = mutableSetOf()
}