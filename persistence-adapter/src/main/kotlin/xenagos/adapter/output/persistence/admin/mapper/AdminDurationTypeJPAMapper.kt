package xenagos.adapter.output.persistence.admin.mapper

import org.springframework.stereotype.Component
import xenagos.adapter.output.persistence.model.DurationTypeJpaEntity
import xenagos.domain.model.DurationType

@Component
class AdminDurationTypeJPAMapper: BaseJPAMapper<DurationType, DurationTypeJpaEntity> {
    override fun toJpaEntity(dEntity: DurationType): DurationTypeJpaEntity =
        DurationTypeJpaEntity().apply {
            id = dEntity.id
            durationType = dEntity.type
            active = dEntity.active
        }

    override fun toDomainEntity(jPAEntity: DurationTypeJpaEntity): DurationType =
        DurationType(
            jPAEntity.id!!,
            jPAEntity.durationType!!,
            jPAEntity.active!!
        )
}