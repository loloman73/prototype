package xenagos.adapter.output.persistence.admin.mapper

import org.springframework.stereotype.Component
import xenagos.adapter.output.persistence.model.LanguageJpaEntity
import xenagos.domain.model.Language

@Component
class AdminLanguageJPAMapper: BaseJPAMapper<Language, LanguageJpaEntity> {

    override fun toJpaEntity(dEntity: Language): LanguageJpaEntity = LanguageJpaEntity().apply {
        id = dEntity.id
        code = dEntity.code
        englishName = dEntity.englishName
        nativeName = dEntity.nativeName
        active = dEntity.active
    }

    //TODO: check for nulls
    override fun toDomainEntity(jPAEntity: LanguageJpaEntity): Language =
        Language(
            jPAEntity.id!!,
            jPAEntity.code!!,
            jPAEntity.englishName!!,
            jPAEntity.nativeName!!,
            jPAEntity.active!!
        )
}