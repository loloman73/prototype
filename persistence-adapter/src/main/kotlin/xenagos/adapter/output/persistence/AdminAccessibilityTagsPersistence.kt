package xenagos.adapter.output.persistence

import org.springframework.stereotype.Repository
import xenagos.adapter.output.persistence.mapper.AdminAccessibilityTagMapper
import xenagos.application.port.output.AdminAccessibilityTagsOutputPort
import xenagos.domain.model.AccessibilityTag
import java.util.*
import kotlin.collections.ArrayList

@Repository
open class AdminAccessibilityTagsPersistence(
    private val repository: AdminAccessibilityTagsRepository,
    private val mapper: AdminAccessibilityTagMapper
) : AdminAccessibilityTagsOutputPort {

    override fun getAllAccessibilityTags(): ArrayList<AccessibilityTag> {
        val accessibilityTagsJpa = repository.findAll()
        val accessibilityTagsDomain = arrayListOf<AccessibilityTag>()
        accessibilityTagsJpa.forEach { accessibilityTagsDomain.add(mapper.jpaEntityToDomain(it)) }
        return accessibilityTagsDomain
    }

    override fun saveNewAccessibilityTag(accessibilityTag: AccessibilityTag): AccessibilityTag {
        val returnedJpaEntity = repository.save(mapper.domainEntityToJpa(accessibilityTag))
        return mapper.jpaEntityToDomain(returnedJpaEntity)
    }

    override fun updateAccessibilityTag(accessibilityTag: AccessibilityTag): AccessibilityTag {
        val returnedJpaEntity = repository.save(mapper.domainEntityToJpa(accessibilityTag))
        return mapper.jpaEntityToDomain(returnedJpaEntity)
    }

    override fun deleteAccessibilityTag(accessibilityTagId: UUID) {
        repository.deleteById(accessibilityTagId)
    }
}