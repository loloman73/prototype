package xenagos.adapter.output.persistence.admin

import org.springframework.stereotype.Repository
import xenagos.adapter.output.persistence.admin.mapper.toDomainEntity
import xenagos.adapter.output.persistence.admin.mapper.toJpaEntity
import xenagos.application.port.output.admin.AdminAccessibilityTagsOutputPort
import xenagos.domain.model.AccessibilityTag
import java.util.*

@Repository
open class AdminAccessibilityTagsPersistence(private val repository: AdminAccessibilityTagsRepository) :
    AdminAccessibilityTagsOutputPort {

    override fun getAll() = arrayListOf<AccessibilityTag>().apply {
        repository.findAll().forEach { add(it.toDomainEntity()) }
    }
    override fun saveOneNew(newEntityToSave: AccessibilityTag): AccessibilityTag =
        repository.save(newEntityToSave.toJpaEntity()).toDomainEntity()

    override fun updateOne(entityToUpdate: AccessibilityTag): AccessibilityTag =
        repository.save(entityToUpdate.toJpaEntity()).toDomainEntity()

    override fun deleteOne(id: UUID) = repository.deleteById(id)
}