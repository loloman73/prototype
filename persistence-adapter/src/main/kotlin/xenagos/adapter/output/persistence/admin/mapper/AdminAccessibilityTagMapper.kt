package xenagos.adapter.output.persistence.admin.mapper

import org.springframework.stereotype.Component
import xenagos.adapter.output.persistence.model.AccessibilityTagJpaEntity
import xenagos.domain.model.AccessibilityTag

@Component
class AdminAccessibilityTagJPAMapper : BaseJPAMapper<AccessibilityTag, AccessibilityTagJpaEntity> {

    override fun toJpaEntity(dEntity: AccessibilityTag): AccessibilityTagJpaEntity =
        AccessibilityTagJpaEntity().apply {
            id = dEntity.id
            accessibilityTag = dEntity.name
            description = dEntity.description
            active = dEntity.active
        }

    //TODO: check for nulls
    override fun toDomainEntity(jPAEntity: AccessibilityTagJpaEntity): AccessibilityTag =
        AccessibilityTag(
            jPAEntity.id!!,
            jPAEntity.accessibilityTag!!,
            jPAEntity.description!!,
            jPAEntity.active!!
        )
}