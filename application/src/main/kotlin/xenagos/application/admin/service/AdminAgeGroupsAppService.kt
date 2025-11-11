package xenagos.application.admin.service

import org.springframework.stereotype.Service
import xenagos.application.admin.mapper.AdminAgeGroupMapper
import xenagos.application.port.input.admin.AdminAgeGroupUseCase
import xenagos.application.port.input.admin.model.AdminAgeGroupUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupResponseDTO
import xenagos.application.port.output.admin.AdminAgeGroupsOutputPort
import xenagos.domain.model.AgeGroup

@Service
//implements Input-Port UseCase Interface AND abstract base class BaseAdminAppService
class AdminAgeGroupsAppService(persistence: AdminAgeGroupsOutputPort, mapper: AdminAgeGroupMapper) :
    BaseAdminAppService<
            AgeGroup,
            AdminAgeGroupNewRequestDTO,
            AdminAgeGroupUpdateRequestDTO,
            AdminAgeGroupResponseDTO>(persistence, mapper),
    AdminAgeGroupUseCase