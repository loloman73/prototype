package xenagos.adapter.input.web.admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import xenagos.application.port.input.admin.AdminLanguagesUseCase
import xenagos.application.port.input.admin.model.AdminLanguageUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminLanguageNewRequestDTO
import xenagos.application.port.input.admin.model.AdminLanguageResponseDTO

@Controller
@RequestMapping("/admin/languages")
class AdminLanguagesController(service: AdminLanguagesUseCase) : BaseAdminController<
        AdminLanguageNewRequestDTO,
        AdminLanguageUpdateRequestDTO,
        AdminLanguageResponseDTO>
    (service) {

    override val myURLEndpoint = "languages"
    override val templateName = "adminLanguages"
    override val fragmentForAddOneNewRequest = "language-modal-form-add-new"
    override val fragmentForUpdateOneRequest = "language-modal-form-edit"

    override fun createEmptyNewRequestDTO() = AdminLanguageNewRequestDTO.createEmptyDeactivated()
    override fun createEmptyUpdateRequestDTO() = AdminLanguageUpdateRequestDTO.createEmptyDeactivated()
}