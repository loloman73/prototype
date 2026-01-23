package xenagos.adapter.input.web.admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import xenagos.application.port.input.admin.AdminTopicTagsUseCase
import xenagos.application.port.input.admin.model.AdminTopicTagUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminTopicTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminTopicTagResponseDTO

@Controller
@RequestMapping("/admin/topic-tags")
class AdminTopicTagsController(service: AdminTopicTagsUseCase) : BaseAdminController<
        AdminTopicTagNewRequestDTO,
        AdminTopicTagUpdateRequestDTO,
        AdminTopicTagResponseDTO>
    (service) {

    override val myURLEndpoint = "topic-tags"
    override val templateName = "adminTopicTags"
    override val fragmentForAddOneNewRequest = "topic-tag-modal-form-add-new"
    override val fragmentForUpdateOneRequest = "topic-tag-modal-form-edit"

    override fun createEmptyNewRequestDTO() = AdminTopicTagNewRequestDTO.createEmptyDeactivated()
    override fun createEmptyUpdateRequestDTO() = AdminTopicTagUpdateRequestDTO.createEmptyDeactivated()
}