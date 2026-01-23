package xenagos.adapter.input.web.admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import xenagos.application.port.input.admin.AdminAmeaTagsUseCase
import xenagos.application.port.input.admin.model.AdminAmeaTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAmeaTagResponseDTO
import xenagos.application.port.input.admin.model.AdminAmeaTagUpdateRequestDTO

@Controller
@RequestMapping("/admin/amea-tags")
class AdminAmeaTagsController(service: AdminAmeaTagsUseCase) : BaseAdminController<
        AdminAmeaTagNewRequestDTO,
        AdminAmeaTagUpdateRequestDTO,
        AdminAmeaTagResponseDTO>
    (service) {

    override val myURLEndpoint = "amea-tags"
    override val templateName = "adminAmeaTags"
    override val fragmentForAddOneNewRequest = "amea-tag-modal-form-add-new"
    override val fragmentForUpdateOneRequest = "amea-tag-modal-form-edit"

    override fun createEmptyNewRequestDTO() = AdminAmeaTagNewRequestDTO.createEmptyDeactivated()
    override fun createEmptyUpdateRequestDTO() = AdminAmeaTagUpdateRequestDTO.createEmptyDeactivated()
}