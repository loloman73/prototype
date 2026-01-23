package xenagos.adapter.input.web.admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import xenagos.application.port.input.admin.AdminAccessibilityTagsUseCase
import xenagos.application.port.input.admin.model.AdminAccessibilityTagUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminAccessibilityTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAccessibilityTagResponseDTO

@Controller
@RequestMapping("/admin/accessibility-tags")
class AdminAccessibilityTagsController(service: AdminAccessibilityTagsUseCase) : BaseAdminController<
        AdminAccessibilityTagNewRequestDTO,
        AdminAccessibilityTagUpdateRequestDTO,
        AdminAccessibilityTagResponseDTO>
    (service) {

    override val myURLEndpoint = "accessibility-tags"
    override val templateName = "adminAccessibilityTags"
    override val fragmentForAddOneNewRequest = "accessibility-tag-modal-form-add-new"
    override val fragmentForUpdateOneRequest = "accessibility-tag-modal-form-edit"

    override fun createEmptyNewRequestDTO() = AdminAccessibilityTagNewRequestDTO.createEmptyDeactivated()
    override fun createEmptyUpdateRequestDTO() = AdminAccessibilityTagUpdateRequestDTO.createEmptyDeactivated()
}