package xenagos.adapter.output.persistence.mapper

import xenagos.adapter.output.persistence.model.AgeGroupJpaEntity
import xenagos.domain.model.AgeGroup

fun AgeGroup.toJpaEntity(): AgeGroupJpaEntity {
    return AgeGroupJpaEntity().apply {
        id = this@toJpaEntity.id
        ageGroup = this@toJpaEntity.groupName
        minAge = this@toJpaEntity.minAge
        maxAge = this@toJpaEntity.maxAge
        active = this@toJpaEntity.active
    }
}

fun AgeGroupJpaEntity.toDomainEntity(): AgeGroup {
    return AgeGroup(
        id = this.id!!,
        groupName = this.ageGroup!!,
        minAge = this.minAge!!,
        maxAge = this.maxAge!!,
        active = this.active!!
    )
}