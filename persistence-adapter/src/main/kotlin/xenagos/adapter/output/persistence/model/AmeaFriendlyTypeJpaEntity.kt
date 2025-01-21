package xenagos.adapter.output.persistence.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "amea_friendly_types")
open class AmeaFriendlyTypeJpaEntity {
    @Id
    @Column(name = "amea_friendly_type_id", nullable = false)
    open var id: UUID? = null

    @Column(name = "amea_friendly_type", length = Integer.MAX_VALUE)
    open var ameaFriendlyType: String? = null

    @OneToMany(mappedBy = "ameaFriendlyType")
    open var mediaGuides: MutableSet<MediaGuideJpaEntity> = mutableSetOf()
}