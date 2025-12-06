package xenagos.application.port.input.admin

import xenagos.application.port.input.admin.model.AdminAmeaTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAmeaTagResponseDTO
import xenagos.application.port.input.admin.model.AdminAmeaTagUpdateRequestDTO

interface AdminAmeaTagsUseCase : BaseAdminUseCase<
        AdminAmeaTagNewRequestDTO,
        AdminAmeaTagUpdateRequestDTO,
        AdminAmeaTagResponseDTO>