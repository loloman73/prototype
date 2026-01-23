package xenagos.adapter.input.web.admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import xenagos.application.port.input.admin.AdminAgeGroupUseCase
import xenagos.application.port.input.admin.model.AdminAgeGroupUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupResponseDTO

@Controller
@RequestMapping("/admin/age-groups")
class AdminAgeGroupsController(service: AdminAgeGroupUseCase) : BaseAdminController<
        AdminAgeGroupNewRequestDTO,
        AdminAgeGroupUpdateRequestDTO,
        AdminAgeGroupResponseDTO>
    (service) {

    override val myURLEndpoint = "age-groups"
    override val templateName = "adminAgeGroups"
    override val fragmentForAddOneNewRequest = "age-group-modal-form-add-new"
    override val fragmentForUpdateOneRequest = "age-group-modal-form-edit"

    override fun createEmptyNewRequestDTO() = AdminAgeGroupNewRequestDTO.createEmptyDeactivated()
    override fun createEmptyUpdateRequestDTO() = AdminAgeGroupUpdateRequestDTO.createEmptyDeactivated()
}