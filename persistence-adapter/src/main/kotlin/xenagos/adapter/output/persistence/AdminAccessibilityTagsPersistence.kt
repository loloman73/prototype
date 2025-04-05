package xenagos.adapter.output.persistence

import org.springframework.stereotype.Repository
import xenagos.adapter.output.persistence.mapper.toDomainEntity
import xenagos.adapter.output.persistence.mapper.toJpaEntity
import xenagos.application.port.output.AdminAccessibilityTagsOutputPort
import xenagos.domain.model.AccessibilityTag
import java.util.*
import kotlin.collections.ArrayList

@Repository
open class AdminAccessibilityTagsPersistence(
    private val repository: AdminAccessibilityTagsRepository
) : AdminAccessibilityTagsOutputPort {

    override fun getAllAccessibilityTags(): ArrayList<AccessibilityTag> {
        val accessibilityTagsDomain = arrayListOf<AccessibilityTag>()
        repository.findAll().forEach { accessibilityTagsDomain.add(it.toDomainEntity()) }
        return accessibilityTagsDomain
    }

    override fun saveNewAccessibilityTag(newEntityToSave: AccessibilityTag): AccessibilityTag {
        val returnedJpaEntity = repository.save(newEntityToSave.toJpaEntity())
        return returnedJpaEntity.toDomainEntity()
    }

    override fun updateAccessibilityTag(entityToUpdate: AccessibilityTag): AccessibilityTag {
        val returnedJpaEntity = repository.save(entityToUpdate.toJpaEntity())
        return returnedJpaEntity.toDomainEntity()
    }

    override fun deleteAccessibilityTag(id: UUID) {
        repository.deleteById(id)
    }
}