package xenagos.adapter.input.web.admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import xenagos.application.port.input.admin.AdminMediaTypesUseCase
import xenagos.application.port.input.admin.model.AdminMediaTypeUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeNewRequestDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeResponseDTO

@Controller
@RequestMapping("/admin/media-types")
class AdminMediaTypesController(service: AdminMediaTypesUseCase) : BaseAdminController<
        AdminMediaTypeNewRequestDTO,
        AdminMediaTypeUpdateRequestDTO,
        AdminMediaTypeResponseDTO>
    (service) {

    override val myURLEndpoint = "media-types"
    override val templateName = "adminMediaTypes"
    override val fragmentForAddOneNewRequest = "media-type-modal-form-add-new"
    override val fragmentForUpdateOneRequest = "media-type-modal-form-edit"

    override fun createEmptyNewRequestDTO() = AdminMediaTypeNewRequestDTO.createEmptyDeactivated()
    override fun createEmptyUpdateRequestDTO() = AdminMediaTypeUpdateRequestDTO.createEmptyDeactivated()
}