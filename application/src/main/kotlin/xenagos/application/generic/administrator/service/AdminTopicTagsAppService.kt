package xenagos.application.generic.administrator.service

import org.springframework.stereotype.Service
import xenagos.application.generic.administrator.mapper.AdminTopicTagMapper
import xenagos.application.port.input.admin.AdminTopicTagsUseCase
import xenagos.application.port.input.admin.model.AdminTopicTagUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminTopicTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminTopicTagResponseDTO
import xenagos.application.port.output.admin.AdminTopicTagsOutputPort
import xenagos.domain.model.TopicTag

@Service
//implements Input-Port UseCase Interface AND abstract base class BaseAdminAppService
class AdminTopicTagsAppService(persistence: AdminTopicTagsOutputPort, mapper: xenagos.application.generic.administrator.mapper.AdminTopicTagMapper) :
    xenagos.application.generic.administrator.service.BaseAdminAppService<
            TopicTag,
            AdminTopicTagNewRequestDTO,
            AdminTopicTagUpdateRequestDTO,
            AdminTopicTagResponseDTO>(persistence, mapper),
    AdminTopicTagsUseCase