package xenagos.adapter.output.persistence.admin

import org.springframework.stereotype.Repository
import xenagos.adapter.output.persistence.admin.mapper.AdminAccessibilityTagJPAMapper
import xenagos.adapter.output.persistence.model.AccessibilityTagJpaEntity
import xenagos.application.port.output.admin.AdminAccessibilityTagsOutputPort
import xenagos.domain.model.AccessibilityTag

@Repository
open class AdminAccessibilityTagsPersistence(
        repository: AdminAccessibilityTagsRepository,
        mapper: AdminAccessibilityTagJPAMapper) :
    BaseAdminPersistence<AccessibilityTag, AccessibilityTagJpaEntity>(repository, mapper),
    AdminAccessibilityTagsOutputPort