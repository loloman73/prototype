package xenagos.application.port.input.admin

import xenagos.application.port.input.admin.model.AdminLanguageUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminLanguageNewRequestDTO
import xenagos.application.port.input.admin.model.AdminLanguageResponseDTO

interface AdminLanguagesUseCase : BaseAdminUseCase<
        AdminLanguageNewRequestDTO,
        AdminLanguageUpdateRequestDTO,
        AdminLanguageResponseDTO>
