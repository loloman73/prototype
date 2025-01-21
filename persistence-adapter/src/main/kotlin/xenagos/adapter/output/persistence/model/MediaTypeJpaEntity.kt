package xenagos.adapter.output.persistence.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "media_types")
open class MediaTypeJpaEntity {
    @Id
    @Column(name = "media_type_id", nullable = false)
    open var id: UUID? = null

    @Column(name = "media_type", length = Integer.MAX_VALUE)
    open var mediaType: String? = null

    @OneToMany(mappedBy = "mediaType")
    open var mediaGuides: MutableSet<MediaGuideJpaEntity> = mutableSetOf()
}