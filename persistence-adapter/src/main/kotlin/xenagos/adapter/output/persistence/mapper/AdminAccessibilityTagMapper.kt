package xenagos.adapter.output.persistence.mapper

import xenagos.adapter.output.persistence.model.AccessibilityTagJpaEntity
import xenagos.domain.model.AccessibilityTag

fun AccessibilityTag.toJpaEntity(): AccessibilityTagJpaEntity {
    return AccessibilityTagJpaEntity().apply {
        id = this@toJpaEntity.id
        accessibilityTag = this@toJpaEntity.name
        description = this@toJpaEntity.description
        active = this@toJpaEntity.active
    }
}

//TODO: check for nulls
fun AccessibilityTagJpaEntity.toDomainEntity(): AccessibilityTag {
    return AccessibilityTag(
        this.id!!,
        this.accessibilityTag!!,
        this.description!!,
        this.active!!
    )
}
