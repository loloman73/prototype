package xenagos.adapter.output.persistence.admin

import org.springframework.stereotype.Repository
import xenagos.adapter.output.persistence.admin.mapper.toDomainEntity
import xenagos.adapter.output.persistence.admin.mapper.toJpaEntity
import xenagos.application.port.output.admin.AdminTopicTagsOutputPort
import xenagos.domain.model.TopicTag
import java.util.*

@Repository
open class AdminTopicTagsPersistence(private val repository: AdminTopicTagsRepository) : AdminTopicTagsOutputPort {

    override fun getAll() = arrayListOf<TopicTag>().apply {
        repository.findAll().forEach { add(it.toDomainEntity()) }
    }
    override fun saveOneNew(newEntityToSave: TopicTag): TopicTag =
        repository.save(newEntityToSave.toJpaEntity()).toDomainEntity()

    override fun updateOne(entityToUpdate: TopicTag): TopicTag =
        repository.save(entityToUpdate.toJpaEntity()).toDomainEntity()

    override fun deleteOne(id: UUID) = repository.deleteById(id)
}