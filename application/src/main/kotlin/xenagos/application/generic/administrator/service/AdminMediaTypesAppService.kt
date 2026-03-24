package xenagos.application.generic.administrator.service

import org.springframework.stereotype.Service
import xenagos.application.generic.administrator.mapper.AdminMediaTypeMapper
import xenagos.application.port.input.admin.AdminMediaTypesUseCase
import xenagos.application.port.input.admin.model.AdminMediaTypeUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeNewRequestDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeResponseDTO
import xenagos.application.port.output.admin.AdminMediaTypesOutputPort
import xenagos.domain.model.MediaType

@Service
//implements Input-Port UseCase Interface AND abstract base class BaseAdminAppService
class AdminMediaTypesAppService(persistence: AdminMediaTypesOutputPort, mapper: xenagos.application.generic.administrator.mapper.AdminMediaTypeMapper) :
    xenagos.application.generic.administrator.service.BaseAdminAppService<
            MediaType,
            AdminMediaTypeNewRequestDTO,
            AdminMediaTypeUpdateRequestDTO,
            AdminMediaTypeResponseDTO>(persistence, mapper),
    AdminMediaTypesUseCase