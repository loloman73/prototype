package xenagos.adapter.output.persistence.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "topic_tags")
open class TopicTagJpaEntity{
    @Id
    @Column(name = "topic_tag_id", nullable = false)
    open var id: UUID? = null

    @Column(name = "topic_tag", length = 35, nullable = false)
    open var topicTag: String? = null

    @Column(name = "description", length = 250, nullable = false)
    open var description: String? = null

    @Column(name = "active", nullable = false)
    open var active: Boolean? = null

    @ManyToMany
    @JoinTable(
        name = "media_guide_topic_tag",
        joinColumns = [JoinColumn(name = "topic_tag_id")],
        inverseJoinColumns = [JoinColumn(name = "media_guide_id")]
    )
    open var mediaGuides: MutableSet<MediaGuideJpaEntity> = mutableSetOf()
}