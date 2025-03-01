package xenagos.adapter.output.persistence.mapper

import xenagos.adapter.output.persistence.model.TopicTagJpaEntity
import xenagos.domain.model.TopicTag

class AdminTopicTagMapper {

    fun domainEntityToJpa(domainEntity: TopicTag): TopicTagJpaEntity {
        return TopicTagJpaEntity().apply {
            id = domainEntity.id
            topicTag = domainEntity.name
            description = domainEntity.description
            active = domainEntity.active
        }
    }


    //TODO check for nulls
    fun jpaEntityToDomain(jpaEntity: TopicTagJpaEntity): TopicTag {
        return TopicTag(
            jpaEntity.id!!,
            jpaEntity.topicTag!!,
            jpaEntity.description!!,
            jpaEntity.active!!)
    }
}