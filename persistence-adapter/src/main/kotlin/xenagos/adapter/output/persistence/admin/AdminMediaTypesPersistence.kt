package xenagos.adapter.output.persistence.admin

import org.springframework.stereotype.Repository
import xenagos.adapter.output.persistence.admin.mapper.AdminMediaTypeJPAMapper
import xenagos.adapter.output.persistence.model.MediaTypeJpaEntity
import xenagos.application.port.output.admin.AdminMediaTypesOutputPort
import xenagos.domain.model.MediaType

@Repository
open class AdminMediaTypesPersistence(
        repository: AdminMediaTypesRepository,
        mapper: AdminMediaTypeJPAMapper) :
    BaseAdminPersistence<MediaType, MediaTypeJpaEntity>(repository, mapper),
    AdminMediaTypesOutputPort