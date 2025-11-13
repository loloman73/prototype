package xenagos.adapter.output.persistence.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "admins")
open class AdminJpaEntity : BaseJPAEntity  {
    @Id
    @Column(name = "admin_id", nullable = false)
    open var id: UUID? = null

    @Column(name = "name", length = Integer.MAX_VALUE)
    open var name: String? = null
}