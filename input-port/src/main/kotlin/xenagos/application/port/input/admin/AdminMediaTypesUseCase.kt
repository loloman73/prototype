package xenagos.application.port.input.admin

import xenagos.application.port.input.admin.model.AdminMediaTypeUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeNewRequestDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeResponseDTO

interface AdminMediaTypesUseCase : BaseAdminUseCase<
        AdminMediaTypeNewRequestDTO,
        AdminMediaTypeUpdateRequestDTO,
        AdminMediaTypeResponseDTO>