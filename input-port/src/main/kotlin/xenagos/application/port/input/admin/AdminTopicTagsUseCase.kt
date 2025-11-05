package xenagos.application.port.input.admin

import xenagos.application.port.input.admin.model.AdminTopicTagUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminTopicTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminTopicTagResponseDTO

interface AdminTopicTagsUseCase : BaseAdminUseCase<
        AdminTopicTagNewRequestDTO,
        AdminTopicTagUpdateRequestDTO,
        AdminTopicTagResponseDTO>