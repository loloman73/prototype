package xenagos.adapter.output.persistence.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "xenagoi")
open class XenagoiJpaEntity : BaseJPAEntity  {
    @Id
    @Column(name = "xenagos_id", nullable = false)
    open var id: UUID? = null

    @Column(name = "name", length = Integer.MAX_VALUE)
    open var name: String? = null

    @Column(name = "photo_file_name")
    open var photoFileName: UUID? = null

    @OneToMany(mappedBy = "xenagos")
    open var tours: MutableSet<TourJpaEntity> = mutableSetOf()
}