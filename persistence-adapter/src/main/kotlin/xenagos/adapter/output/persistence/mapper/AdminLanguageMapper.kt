package xenagos.adapter.output.persistence.mapper

import xenagos.adapter.output.persistence.model.LanguageJpaEntity
import xenagos.domain.model.Language

fun Language.toJpaEntity(): LanguageJpaEntity {
    return LanguageJpaEntity().apply {
        id = this@toJpaEntity.id
        code = this@toJpaEntity.code
        englishName = this@toJpaEntity.englishName
        nativeName = this@toJpaEntity.nativeName
        active = this@toJpaEntity.active
    }
}

//TODO: check for nulls
fun LanguageJpaEntity.toDomainEntity(): Language {
    return Language(
        id = this.id!!,
        code = this.code!!,
        englishName = this.englishName!!,
        nativeName = this.nativeName!!,
        active = this.active!!
    )
}