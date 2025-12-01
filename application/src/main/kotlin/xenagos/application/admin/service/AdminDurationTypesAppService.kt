package xenagos.application.admin.service

import org.springframework.stereotype.Service
import xenagos.application.admin.mapper.AdminDurationTypeMapper
import xenagos.application.port.input.admin.AdminDurationTypesUseCase
import xenagos.application.port.input.admin.model.AdminDurationTypeNewRequestDTO
import xenagos.application.port.input.admin.model.AdminDurationTypeResponseDTO
import xenagos.application.port.input.admin.model.AdminDurationTypeUpdateRequestDTO
import xenagos.application.port.output.admin.AdminDurationTypesOutputPort
import xenagos.domain.model.DurationType

@Service
class AdminDurationTypesAppService(persistence: AdminDurationTypesOutputPort, mapper: AdminDurationTypeMapper) :
    BaseAdminAppService<
            DurationType,
            AdminDurationTypeNewRequestDTO,
            AdminDurationTypeUpdateRequestDTO,
            AdminDurationTypeResponseDTO>(persistence, mapper),
    AdminDurationTypesUseCase