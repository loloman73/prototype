package xenagos.adapter.output.persistence.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "duration_types")
open class DurationType {
    @Id
    @Column(name = "duration_type_id", nullable = false)
    open var id: UUID? = null

    @Column(name = "duration_type", length = Integer.MAX_VALUE)
    open var durationType: String? = null

    @OneToMany(mappedBy = "durationType")
    open var mediaGuides: MutableSet<MediaGuide> = mutableSetOf()
}