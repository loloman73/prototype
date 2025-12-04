package xenagos.adapter.output.persistence.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "amea_tags")
open class AmeaTagJpaEntity : BaseJPAEntity  {
    @Id
    @Column(name = "amea_tag_id", nullable = false)
    open var id: UUID? = null

    @Column(name = "amea_tag", length = 35, nullable = false)
    open var ameaTag: String? = null

    @Column(name = "description", length = 250, nullable = false)
    open var description: String? = null

    @Column(name = "active", nullable = false)
    open var active: Boolean? = null

    @OneToMany(mappedBy = "ameaTag")
    open var mediaGuides: MutableSet<MediaGuideJpaEntity> = mutableSetOf()
}