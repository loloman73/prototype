package xenagos.adapter.output.persistence.admin

import org.springframework.stereotype.Repository
import xenagos.adapter.output.persistence.admin.mapper.AdminAgeGroupJPAMapper
import xenagos.adapter.output.persistence.model.AgeGroupJpaEntity
import xenagos.application.port.output.admin.AdminAgeGroupsOutputPort
import xenagos.domain.model.AgeGroup

@Repository
open class AdminAgeGroupsPersistence(
        repository: AdminAgeGroupsRepository,
        mapper: AdminAgeGroupJPAMapper) :
    BaseAdminPersistence<AgeGroup, AgeGroupJpaEntity>(repository, mapper),
    AdminAgeGroupsOutputPort