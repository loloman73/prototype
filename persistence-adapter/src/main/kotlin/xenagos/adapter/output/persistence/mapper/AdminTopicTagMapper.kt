package xenagos.adapter.output.persistence.mapper

import xenagos.adapter.output.persistence.model.TopicTagJpaEntity
import xenagos.domain.model.TopicTag

fun TopicTag.toJpaEntity(): TopicTagJpaEntity {
    return TopicTagJpaEntity().apply {
        id = this@toJpaEntity.id
        topicTag = this@toJpaEntity.name
        description = this@toJpaEntity.description
        active = this@toJpaEntity.active
    }
}

//TODO: check for nulls
fun TopicTagJpaEntity.toDomainEntity():TopicTag{
    return TopicTag(
        this.id!!,
        this.topicTag!!,
        this.description!!,
        this.active!!
    )
}