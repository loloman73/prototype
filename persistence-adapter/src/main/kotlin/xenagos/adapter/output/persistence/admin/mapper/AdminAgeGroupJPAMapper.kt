package xenagos.adapter.output.persistence.admin.mapper

import org.springframework.stereotype.Component
import xenagos.adapter.output.persistence.model.AgeGroupJpaEntity
import xenagos.domain.model.AgeGroup

@Component
class AdminAgeGroupJPAMapper : BaseJPAMapper<AgeGroup, AgeGroupJpaEntity> {

    override fun toJpaEntity(dEntity: AgeGroup): AgeGroupJpaEntity = AgeGroupJpaEntity().apply {
        id = dEntity.id
        ageGroup = dEntity.name
        minAge = dEntity.minAge
        maxAge = dEntity.maxAge
        active = dEntity.active
    }
    //TODO: check for nulls
    override fun toDomainEntity(jPAEntity: AgeGroupJpaEntity): AgeGroup = AgeGroup(
        jPAEntity.id!!,
        jPAEntity.ageGroup!!,
        jPAEntity.minAge!!,
        jPAEntity.maxAge!!,
        jPAEntity.active!!
    )
}