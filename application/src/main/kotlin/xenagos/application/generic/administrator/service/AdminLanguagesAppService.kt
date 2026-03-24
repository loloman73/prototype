package xenagos.application.generic.administrator.service

import org.springframework.stereotype.Service
import xenagos.application.generic.administrator.mapper.AdminLanguageMapper
import xenagos.application.port.input.admin.AdminLanguagesUseCase
import xenagos.application.port.input.admin.model.AdminLanguageUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminLanguageNewRequestDTO
import xenagos.application.port.input.admin.model.AdminLanguageResponseDTO
import xenagos.application.port.output.admin.AdminLanguagesOutputPort
import xenagos.domain.model.Language

@Service
//implements Input-Port UseCase Interface AND abstract base class BaseAdminAppService
class AdminLanguagesAppService(persistence: AdminLanguagesOutputPort, mapper: xenagos.application.generic.administrator.mapper.AdminLanguageMapper) :
    xenagos.application.generic.administrator.service.BaseAdminAppService<
            Language,
            AdminLanguageNewRequestDTO,
            AdminLanguageUpdateRequestDTO,
            AdminLanguageResponseDTO>(persistence, mapper),
    AdminLanguagesUseCase