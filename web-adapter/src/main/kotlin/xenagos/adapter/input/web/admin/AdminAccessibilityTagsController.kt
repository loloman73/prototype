package xenagos.adapter.input.web.admin

import jakarta.servlet.http.HttpServletResponse
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import xenagos.application.port.input.admin.AdminAccessibilityTagsUseCase
import xenagos.application.port.input.admin.model.AdminAccessibilityTagUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminAccessibilityTagNewRequestDTO
import java.util.UUID

@Controller
@RequestMapping("/admin/accessibility-tags")
class AdminAccessibilityTagsController(private val service: AdminAccessibilityTagsUseCase) : BaseAdminController() {

    override val fragmentForAddOneNewRequest: String = "accessibility-tag-modal-form-add-new"
    override val fragmentForUpdateOneRequest: String = "accessibility-tag-modal-form-edit"
    override val myEndpointPath: String = "accessibility-tags"
    override val emptyNewRequestDTO = AdminAccessibilityTagNewRequestDTO.createEmptyDeactivated()
    override val emptyUpdateRequestDTO = AdminAccessibilityTagUpdateRequestDTO.createEmptyDeactivated()

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("listAllModel", service.getAll())
        model.addAttribute("addOneNewModel", emptyNewRequestDTO)
        model.addAttribute("updateOneModel", emptyUpdateRequestDTO)
        return "adminAccessibilityTags"
    }

    @HxRequest
    @PostMapping
    fun addOneNew(
        @Valid @ModelAttribute("addOneNewModel")
        requestDTO: AdminAccessibilityTagNewRequestDTO,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): String = handleAddOneNew(bindingResult, response) { service.saveOneNew(requestDTO) }

    @HxRequest
    @PutMapping
    fun updateOne(
        @Valid @ModelAttribute("updateOneModel")
        requestDTO: AdminAccessibilityTagUpdateRequestDTO,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): String = handleUpdateOne(bindingResult, response) { service.updateOne(requestDTO) }

    @HxRequest
    @DeleteMapping
    fun deleteOne(@RequestParam id: UUID, response: HttpServletResponse): String =
        handleDeleteOne(response) { service.deleteOne(id) }
}