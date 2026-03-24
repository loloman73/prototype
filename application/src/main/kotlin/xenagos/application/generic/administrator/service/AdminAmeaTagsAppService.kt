package xenagos.application.generic.administrator.service

import org.springframework.stereotype.Service
import xenagos.application.generic.administrator.mapper.AdminAmeaTagMapper
import xenagos.application.port.input.admin.AdminAmeaTagsUseCase
import xenagos.application.port.input.admin.model.AdminAmeaTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAmeaTagResponseDTO
import xenagos.application.port.input.admin.model.AdminAmeaTagUpdateRequestDTO
import xenagos.application.port.output.admin.AdminAmeaTagsOutputPort
import xenagos.domain.model.AmeaTag

@Service
class AdminAmeaTagsAppService(
    persistence: AdminAmeaTagsOutputPort,
    mapper: xenagos.application.generic.administrator.mapper.AdminAmeaTagMapper
) : xenagos.application.generic.administrator.service.BaseAdminAppService<
        AmeaTag,
        AdminAmeaTagNewRequestDTO,
        AdminAmeaTagUpdateRequestDTO,
        AdminAmeaTagResponseDTO>(persistence, mapper),
    AdminAmeaTagsUseCase