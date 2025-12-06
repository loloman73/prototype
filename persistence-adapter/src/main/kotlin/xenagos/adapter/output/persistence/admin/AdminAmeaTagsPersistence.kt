package xenagos.adapter.output.persistence.admin

import org.springframework.stereotype.Repository
import xenagos.adapter.output.persistence.admin.mapper.AdminAmeaTagJPAMapper
import xenagos.adapter.output.persistence.model.AmeaTagJpaEntity
import xenagos.application.port.output.admin.AdminAmeaTagsOutputPort
import xenagos.domain.model.AmeaTag

@Repository
open class AdminAmeaTagsPersistence(repository: AdminAmeaTagsRepository, mapper: AdminAmeaTagJPAMapper) :
    BaseAdminPersistence<AmeaTag, AmeaTagJpaEntity>(repository, mapper),
    AdminAmeaTagsOutputPort