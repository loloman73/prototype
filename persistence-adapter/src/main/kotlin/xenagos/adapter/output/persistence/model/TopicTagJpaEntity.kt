package xenagos.adapter.output.persistence.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "topic_tags")
open class TopicTagJpaEntity {
    @Id
    @Column(name = "topic_tag_id", nullable = false)
    open var id: UUID? = null

    @Column(name = "topic_tag", length = Integer.MAX_VALUE)
    open var topicTag: String? = null

    @Column(name = "description", length = Integer.MAX_VALUE)
    open var description: String? = null

    @ManyToMany
    @JoinTable(
        name = "media_guide_topic_tag",
        joinColumns = [JoinColumn(name = "topic_tag_id")],
        inverseJoinColumns = [JoinColumn(name = "media_guide_id")]
    )
    open var mediaGuides: MutableSet<MediaGuideJpaEntity> = mutableSetOf()
}