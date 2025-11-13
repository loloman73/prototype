package xenagos.adapter.output.persistence.admin

import org.springframework.stereotype.Repository
import xenagos.adapter.output.persistence.admin.mapper.AdminLanguageJPAMapper
import xenagos.adapter.output.persistence.model.LanguageJpaEntity
import xenagos.application.port.output.admin.AdminLanguagesOutputPort
import xenagos.domain.model.Language

@Repository
open class AdminLanguagesPersistence(
        repository: AdminLanguagesRepository,
        mapper: AdminLanguageJPAMapper) :
    BaseAdminPersistence<Language, LanguageJpaEntity>(repository, mapper),
    AdminLanguagesOutputPort