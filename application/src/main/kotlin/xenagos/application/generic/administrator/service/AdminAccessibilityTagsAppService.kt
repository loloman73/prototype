package xenagos.application.generic.administrator.service

import org.springframework.stereotype.Service
import xenagos.application.generic.administrator.mapper.AdminAccessibilityTagMapper
import xenagos.application.port.input.admin.AdminAccessibilityTagsUseCase
import xenagos.application.port.input.admin.model.AdminAccessibilityTagUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminAccessibilityTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAccessibilityTagResponseDTO
import xenagos.application.port.output.admin.AdminAccessibilityTagsOutputPort
import xenagos.domain.model.AccessibilityTag

@Service
//implements Input-Port UseCase Interface AND abstract base class BaseAdminAppService
class AdminAccessibilityTagsAppService(
    persistence: AdminAccessibilityTagsOutputPort,
    mapper: xenagos.application.generic.administrator.mapper.AdminAccessibilityTagMapper
) : xenagos.application.generic.administrator.service.BaseAdminAppService<
        AccessibilityTag,
        AdminAccessibilityTagNewRequestDTO,
        AdminAccessibilityTagUpdateRequestDTO,
        AdminAccessibilityTagResponseDTO>(persistence, mapper),
    AdminAccessibilityTagsUseCase