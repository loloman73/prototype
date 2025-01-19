package xenagos.adapter.output.persistence.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Entity
import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*

@Embeddable
open class MediaGuideAgeRangeId : Serializable {
    @Column(name = "media_guide_id", nullable = false)
    open var mediaGuideId: UUID? = null

    @Column(name = "age_range_id", nullable = false)
    open var ageRangeId: UUID? = null
    override fun hashCode(): Int = Objects.hash(mediaGuideId, ageRangeId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as MediaGuideAgeRangeId

        return mediaGuideId == other.mediaGuideId &&
                ageRangeId == other.ageRangeId
    }

    companion object {
        private const val serialVersionUID = 6101093818970978129L
    }
}