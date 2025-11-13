package xenagos.adapter.output.persistence.admin.mapper

import org.springframework.stereotype.Component
import xenagos.adapter.output.persistence.model.MediaTypeJpaEntity
import xenagos.domain.model.MediaType

@Component
class AdminMediaTypeJPAMapper : BaseJPAMapper<MediaType, MediaTypeJpaEntity> {

    override fun toJpaEntity(dEntity: MediaType): MediaTypeJpaEntity = MediaTypeJpaEntity().apply {
        id = dEntity.id
        mediaType = dEntity.type
        active = dEntity.active
    }

    //TODO: check for nulls
    override fun toDomainEntity(jPAEntity: MediaTypeJpaEntity): MediaType = MediaType(
        jPAEntity.id!!,
        jPAEntity.mediaType!!,
        jPAEntity.active!!
    )
}