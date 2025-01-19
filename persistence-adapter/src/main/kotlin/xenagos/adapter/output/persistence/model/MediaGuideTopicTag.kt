package xenagos.adapter.output.persistence.model

import jakarta.persistence.*

@Entity
@Table(name = "media_guide_topic_tag")
open class MediaGuideTopicTag {
    @EmbeddedId
    open var id: MediaGuideTopicTagId? = null

    @MapsId("topicTagId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "topic_tag_id", nullable = false)
    open var topicTag: TopicTag? = null

    @MapsId("mediaGuideId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "media_guide_id", nullable = false)
    open var mediaGuide: MediaGuide? = null
}