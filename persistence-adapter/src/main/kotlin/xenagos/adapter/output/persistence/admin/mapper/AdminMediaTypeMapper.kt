package xenagos.adapter.output.persistence.admin.mapper

import xenagos.adapter.output.persistence.model.MediaTypeJpaEntity
import xenagos.domain.model.MediaType

fun MediaType.toJpaEntity() = MediaTypeJpaEntity().apply {
    id = this@toJpaEntity.id
    mediaType = this@toJpaEntity.type
    active = this@toJpaEntity.active
}

//TODO: check for nulls
fun MediaTypeJpaEntity.toDomainEntity() = MediaType(
    id = this.id!!,
    type = this.mediaType!!,
    active = this.active!!
)