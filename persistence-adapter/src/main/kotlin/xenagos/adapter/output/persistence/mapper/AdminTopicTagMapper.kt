package xenagos.adapter.output.persistence.mapper

import xenagos.adapter.output.persistence.model.TopicTagJpaEntity
import xenagos.domain.model.TopicTag

fun TopicTag.toJpaEntity(): TopicTagJpaEntity {
    return TopicTagJpaEntity().apply {
        id = this@toJpaEntity.id
        topicTag = this@toJpaEntity.tagName
        description = this@toJpaEntity.description
        active = this@toJpaEntity.active
    }
}

//TODO: check for nulls
fun TopicTagJpaEntity.toDomainEntity(): TopicTag {
    return TopicTag(
        id = this.id!!,
        tagName = this.topicTag!!,
        description = this.description!!,
        active = this.active!!
    )
}