package xenagos.adapter.output.persistence.admin.mapper

import xenagos.domain.model.BaseDomainEntity

interface BaseJPAMapper<
        TDomainEntity : BaseDomainEntity,
        TJPAEntity> {
    fun toJpaEntity(dEntity: TDomainEntity): TJPAEntity
    fun toDomainEntity(jPAEntity: TJPAEntity): TDomainEntity
}