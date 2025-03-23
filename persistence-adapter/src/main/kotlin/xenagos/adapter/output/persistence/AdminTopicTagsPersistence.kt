package xenagos.adapter.output.persistence

import org.springframework.stereotype.Repository
import xenagos.adapter.output.persistence.mapper.AdminTopicTagMapper
import xenagos.application.port.output.AdminTopicTagsOutputPort
import xenagos.domain.model.TopicTag
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

@Repository
open class AdminTopicTagsPersistence(
    private val repository: AdminTopicTagsRepository,
    private val mapper: AdminTopicTagMapper
) : AdminTopicTagsOutputPort {

    override fun getAllTopicTags(): ArrayList<TopicTag> {

        val topicTagsJpa = repository.findAll()
        val topicTagsDomain = arrayListOf<TopicTag>()
        topicTagsJpa.forEach{topicTagsDomain.add(mapper.jpaEntityToDomain(it))}

        return topicTagsDomain
    }

    override fun saveNewTopicTag(topicTag: TopicTag): TopicTag {
        val returnedJpaEntity = repository.save(mapper.domainEntityToJpa(topicTag))
        return mapper.jpaEntityToDomain(returnedJpaEntity)
    }

    override fun updateTopicTag(topicTag: TopicTag): TopicTag {
        val returnedJpaEntity = repository.save(mapper.domainEntityToJpa(topicTag))
        return mapper.jpaEntityToDomain(returnedJpaEntity)
    }


    override fun deleteTopicTag(topicTagId: UUID) {
        repository.deleteById(topicTagId)
    }

    private fun mockTopic(): TopicTag {
        return TopicTag(
            id = UUID.randomUUID(),
            name = RandomText.getWords(3),
            description = RandomText.getWords(15),
            active = Random.nextBoolean()
        )
    }

}