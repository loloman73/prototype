package xenagos.adapter.output.persistence.mapper

import org.springframework.stereotype.Component
import xenagos.adapter.output.persistence.model.AccessibilityTagJpaEntity
import xenagos.domain.model.AccessibilityTag

@Component
class AdminAccessibilityTagMapper {

    fun domainEntityToJpa(domainEntity:AccessibilityTag): AccessibilityTagJpaEntity {
        return AccessibilityTagJpaEntity().apply {
            id = domainEntity.id
            accessibilityTag= domainEntity.name
            description = domainEntity.description
            active = domainEntity.active
        }
    }

    fun jpaEntityToDomain(jpaEntity: AccessibilityTagJpaEntity):AccessibilityTag{
        return AccessibilityTag(
            jpaEntity.id!!,
            jpaEntity.accessibilityTag!!,
            jpaEntity.description!!,
            jpaEntity.active!!
        )
    }
}