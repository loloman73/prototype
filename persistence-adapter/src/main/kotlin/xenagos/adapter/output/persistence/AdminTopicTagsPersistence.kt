package xenagos.adapter.output.persistence

import org.springframework.stereotype.Repository
import xenagos.adapter.output.persistence.mapper.toDomainEntity
import xenagos.adapter.output.persistence.mapper.toJpaEntity
import xenagos.application.port.output.AdminTopicTagsOutputPort
import xenagos.domain.model.TopicTag
import java.util.*
import kotlin.collections.ArrayList

@Repository
open class AdminTopicTagsPersistence(private val repository: AdminTopicTagsRepository) : AdminTopicTagsOutputPort {

    override fun getAllTopicTags(): ArrayList<TopicTag> {
        val domainEntityList = arrayListOf<TopicTag>()
        repository.findAll().forEach { domainEntityList.add(it.toDomainEntity()) }
        return domainEntityList
    }

    override fun saveNewTopicTag(newEntityToSave: TopicTag): TopicTag {
        val returnedJpaEntity = repository.save(newEntityToSave.toJpaEntity())
        return returnedJpaEntity.toDomainEntity()
    }

    override fun updateTopicTag(entityToUpdate: TopicTag): TopicTag {
        val returnedJpaEntity = repository.save(entityToUpdate.toJpaEntity())
        return returnedJpaEntity.toDomainEntity()
    }

    override fun deleteTopicTag(id: UUID) {
        repository.deleteById(id)
    }
}