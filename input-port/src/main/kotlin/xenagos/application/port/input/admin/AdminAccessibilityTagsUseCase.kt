package xenagos.application.port.input.admin

import xenagos.application.port.input.admin.model.AdminAccessibilityTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAccessibilityTagResponseDTO
import xenagos.application.port.input.admin.model.AdminAccessibilityTagUpdateRequestDTO

interface AdminAccessibilityTagsUseCase : BaseAdminUseCase<
        AdminAccessibilityTagNewRequestDTO,
        AdminAccessibilityTagUpdateRequestDTO,
        AdminAccessibilityTagResponseDTO>


