package xenagos.adapter.output.persistence.admin.mapper

import org.springframework.stereotype.Component
import xenagos.adapter.output.persistence.model.AmeaTagJpaEntity
import xenagos.domain.model.AmeaTag

@Component
class AdminAmeaTagJPAMapper : BaseJPAMapper<AmeaTag, AmeaTagJpaEntity> {
    override fun toJpaEntity(dEntity: AmeaTag): AmeaTagJpaEntity =
        AmeaTagJpaEntity().apply {
            id = dEntity.id
            ameaTag = dEntity.name
            description = dEntity.description
            active = dEntity.active
        }

    //TODO: check for nulls
    override fun toDomainEntity(jPAEntity: AmeaTagJpaEntity): AmeaTag =
        AmeaTag(
            jPAEntity.id!!,
            jPAEntity.ameaTag!!,
            jPAEntity.description!!,
            jPAEntity.active!!
        )
}