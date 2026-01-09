package xenagos.adapter.input.web.admin

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import xenagos.application.port.input.admin.AdminAmeaTagsUseCase
import xenagos.application.port.input.admin.model.AdminAmeaTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAmeaTagUpdateRequestDTO
import java.util.UUID

@Controller
@RequestMapping("/admin/amea-tags")
class AdminAmeaTagsController(private val service: AdminAmeaTagsUseCase) : BaseAdminController() {

    override val fragmentForAddOneNewRequest = "amea-tag-modal-form-add-new"
    override val fragmentForUpdateOneRequest = "amea-tag-modal-form-edit"
    override val myURLEndpoint = "amea-tags"
    override val emptyNewRequestDTO = AdminAmeaTagNewRequestDTO.createEmptyDeactivated()
    override val emptyUpdateRequestDTO = AdminAmeaTagUpdateRequestDTO.createEmptyDeactivated()

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("addOneNewModel", emptyNewRequestDTO)
        model.addAttribute("updateOneModel", emptyUpdateRequestDTO)
        return ADMIN_TEMPLATE_PATH_PREFIX + "adminAmeaTags"
    }

    @HxRequest
    @PostMapping
    fun addOneNew(
        @Valid @ModelAttribute("addOneNewModel")
        requestDTO: AdminAmeaTagNewRequestDTO,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): String = handleAddOneNew(bindingResult, response) { service.saveOneNew(requestDTO) }

    @HxRequest
    @PutMapping
    fun updateOne(
        @Valid @ModelAttribute("updateOneModel")
        requestDTO: AdminAmeaTagUpdateRequestDTO,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): String = handleUpdateOne(bindingResult, response) { service.updateOne(requestDTO) }

    @HxRequest
    @DeleteMapping("/{id}")
    fun deleteOne(
        @PathVariable id: UUID,
        response: HttpServletResponse
    ): String = handleDeleteOne(response) { service.deleteOne(id) }
}