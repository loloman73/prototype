package xenagos.adapter.output.persistence.admin

import org.springframework.stereotype.Repository
import xenagos.adapter.output.persistence.admin.mapper.AdminDurationTypeJPAMapper
import xenagos.adapter.output.persistence.model.DurationTypeJpaEntity
import xenagos.application.port.output.admin.AdminDurationTypesOutputPort
import xenagos.domain.model.DurationType

@Repository
open class AdminDurationTypesPersistence(
    repository: AdminDurationTypesRepository,
    mapper: AdminDurationTypeJPAMapper
) :
    BaseAdminPersistence<DurationType, DurationTypeJpaEntity>(repository, mapper),
    AdminDurationTypesOutputPort