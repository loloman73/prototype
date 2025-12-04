package xenagos.adapter.output.persistence.admin.mapper

import org.springframework.stereotype.Component
import xenagos.adapter.output.persistence.model.TopicTagJpaEntity
import xenagos.domain.model.TopicTag

@Component
class AdminTopicTagJPAMapper : BaseJPAMapper<TopicTag, TopicTagJpaEntity> {
    override fun toJpaEntity(dEntity: TopicTag): TopicTagJpaEntity = TopicTagJpaEntity().apply {
        id = dEntity.id
        topicTag = dEntity.name
        description = dEntity.description
        active = dEntity.active
    }

    //TODO: check for nulls
    override fun toDomainEntity(jPAEntity: TopicTagJpaEntity): TopicTag = TopicTag(
        jPAEntity.id!!,
        jPAEntity.topicTag!!,
        jPAEntity.description!!,
        jPAEntity.active!!
    )
}