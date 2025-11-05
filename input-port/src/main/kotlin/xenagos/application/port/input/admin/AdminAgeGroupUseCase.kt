package xenagos.application.port.input.admin

import xenagos.application.port.input.admin.model.AdminAgeGroupResponseDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupUpdateRequestDTO

interface AdminAgeGroupUseCase : BaseAdminUseCase<
        AdminAgeGroupNewRequestDTO,
        AdminAgeGroupUpdateRequestDTO,
        AdminAgeGroupResponseDTO>