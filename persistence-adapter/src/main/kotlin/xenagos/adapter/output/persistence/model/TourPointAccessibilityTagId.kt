package xenagos.adapter.output.persistence.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*

@Embeddable
open class TourPointAccessibilityTagId : Serializable {
    @Column(name = "tour_point_id", nullable = false)
    open var tourPointId: UUID? = null

    @Column(name = "accessibility_tag_id", nullable = false)
    open var accessibilityTagId: UUID? = null
    override fun hashCode(): Int = Objects.hash(tourPointId, accessibilityTagId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as TourPointAccessibilityTagId

        return tourPointId == other.tourPointId &&
                accessibilityTagId == other.accessibilityTagId
    }

    companion object {
        private const val serialVersionUID = -5177586632558516063L
    }
}