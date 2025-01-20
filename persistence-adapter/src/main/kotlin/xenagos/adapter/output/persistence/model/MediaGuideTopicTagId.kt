package xenagos.adapter.output.persistence.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*

@Embeddable
open class MediaGuideTopicTagId : Serializable {

    @Column(name = "topic_tag_id", nullable = false)
    open var topicTagId: UUID? = null

    @Column(name = "media_guide_id", nullable = false)
    open var mediaGuideId: UUID? = null

    override fun hashCode(): Int = Objects.hash(topicTagId, mediaGuideId)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as MediaGuideTopicTagId

        return topicTagId == other.topicTagId &&
                mediaGuideId == other.mediaGuideId
    }

    companion object {
        private const val serialVersionUID = 7161536210608267920L
    }
}