package xenagos.adapter.output.persistence.admin.mapper

import xenagos.adapter.output.persistence.model.BaseJPAEntity
import xenagos.domain.model.BaseDomainEntity

interface BaseJPAMapper<
        TDomainEntity : BaseDomainEntity,
        TJPAEntity : BaseJPAEntity> {
    fun toJpaEntity(dEntity: TDomainEntity): TJPAEntity
    fun toDomainEntity(jPAEntity: TJPAEntity): TDomainEntity
}