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
import java.util.*

@Controller
@RequestMapping("/admin/accessibilityTags")
class AdminAccessibilityTagsController(private val service: AdminAccessibilityTagsUseCase) : BaseAdminController() {

    override val fragmentForAddOneNewRequest: String = "accessibility-tag-modal-form-add-new"
    override val fragmentForUpdateOneRequest: String = "accessibility-tag-modal-form-edit"
    override val myEndpointPath: String = "accessibilityTags"
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
    @PostMapping("/addNew")
    fun addOneNew(
        @ModelAttribute("addOneNewModel")
        @Valid
        requestDTO: AdminAccessibilityTagNewRequestDTO,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): String = handleAddOneNew(bindingResult, response) { service.saveOneNew(requestDTO) }

    @HxRequest
    @PutMapping("/edit")
    fun updateOne(
        @Valid @ModelAttribute("updateOneModel")
        requestDTO: AdminAccessibilityTagUpdateRequestDTO,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): String = handleUpdateOne(bindingResult, response) { service.updateOne(requestDTO) }

    @HxRequest
    @DeleteMapping("/delete")
    fun deleteOne(@RequestParam id: UUID, response: HttpServletResponse): String =
        handleDeleteOne(response) { service.deleteOne(id) }

}