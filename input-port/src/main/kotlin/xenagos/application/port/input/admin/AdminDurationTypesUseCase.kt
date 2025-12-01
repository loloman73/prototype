package xenagos.application.port.input.admin

import xenagos.application.port.input.admin.model.AdminDurationTypeNewRequestDTO
import xenagos.application.port.input.admin.model.AdminDurationTypeResponseDTO
import xenagos.application.port.input.admin.model.AdminDurationTypeUpdateRequestDTO

interface AdminDurationTypesUseCase : BaseAdminUseCase<
        AdminDurationTypeNewRequestDTO,
        AdminDurationTypeUpdateRequestDTO,
        AdminDurationTypeResponseDTO> {
}