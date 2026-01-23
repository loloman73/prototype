package xenagos.adapter.input.web.admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import xenagos.application.port.input.admin.AdminDurationTypesUseCase
import xenagos.application.port.input.admin.model.AdminDurationTypeNewRequestDTO
import xenagos.application.port.input.admin.model.AdminDurationTypeResponseDTO
import xenagos.application.port.input.admin.model.AdminDurationTypeUpdateRequestDTO

@Controller
@RequestMapping("/admin/duration-types")
class AdminDurationTypesController(service: AdminDurationTypesUseCase) : BaseAdminController<
        AdminDurationTypeNewRequestDTO,
        AdminDurationTypeUpdateRequestDTO,
        AdminDurationTypeResponseDTO>
    (service) {

    override val myURLEndpoint = "duration-types"
    override val templateName = "adminDurationTypes"
    override val fragmentForAddOneNewRequest = "duration-type-modal-form-add-new"
    override val fragmentForUpdateOneRequest = "duration-type-modal-form-edit"

    override fun createEmptyNewRequestDTO() = AdminDurationTypeNewRequestDTO.createEmptyDeactivated()
    override fun createEmptyUpdateRequestDTO() = AdminDurationTypeUpdateRequestDTO.createEmptyDeactivated()
}