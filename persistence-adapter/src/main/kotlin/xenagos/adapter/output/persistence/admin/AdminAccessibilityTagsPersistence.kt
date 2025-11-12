package xenagos.adapter.output.persistence.admin

import org.springframework.stereotype.Repository
import xenagos.adapter.output.persistence.admin.mapper.AdminAccessibilityTagJPAMapper
import xenagos.application.port.output.admin.AdminAccessibilityTagsOutputPort
import xenagos.domain.model.AccessibilityTag
import java.util.UUID

@Repository
open class AdminAccessibilityTagsPersistence(
    private val repository: AdminAccessibilityTagsRepository,
    private val mapper: AdminAccessibilityTagJPAMapper
) : AdminAccessibilityTagsOutputPort {

    override fun getAll() = arrayListOf<AccessibilityTag>().apply {
        repository.findAll().forEach { add(mapper.toDomainEntity(it)) }
    }
    override fun saveOneNew(newEntityToSave: AccessibilityTag): AccessibilityTag =
        mapper.toDomainEntity(repository.save(mapper.toJpaEntity(newEntityToSave)))

    override fun updateOne(entityToUpdate: AccessibilityTag): AccessibilityTag =
        mapper.toDomainEntity(repository.save(mapper.toJpaEntity(entityToUpdate)))

    override fun deleteOne(id: UUID) = repository.deleteById(id)
}