package xenagos.adapter.output.persistence.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "tourist_guides")
open class TouristGuideJpaEntity : BaseJPAEntity  {
    @Id
    @Column(name = "tourist_guide_id", nullable = false)
    open var id: UUID? = null

    @Column(name = "name", length = Integer.MAX_VALUE)
    open var name: String? = null

    @Column(name = "photo_file_name")
    open var photoFileName: UUID? = null

    @OneToMany(mappedBy = "tourist_guide")
    open var tours: MutableSet<TourJpaEntity> = mutableSetOf()
}