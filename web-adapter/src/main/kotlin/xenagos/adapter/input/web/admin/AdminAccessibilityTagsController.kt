package xenagos.adapter.input.web.admin

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

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("accessibilityTags", service.getAll())
        model.addAttribute("addNewAccessibilityTag", AdminAccessibilityTagNewRequestDTO("", "", false))
        model.addAttribute(
            "editAccessibilityTag",
            AdminAccessibilityTagUpdateRequestDTO(UUID.randomUUID(), "", "", false)
        )
        return "adminAccessibilityTags"
    }

    @HxRequest
    @PostMapping("/addNew")
    fun addOneNew(
        @Valid @ModelAttribute("addNewAccessibilityTag") addNewAccessibilityTagDTO: AdminAccessibilityTagNewRequestDTO,
        bindingResult: BindingResult
    ): String = handleAddNew(
        bindingResult = bindingResult,
        errorFragmentName = "accessibility-tag-modal-form-add-new",
        redirectPath = "accessibilityTags"
    ) {
        service.saveOneNew(addNewAccessibilityTagDTO)
    }

    @HxRequest
    @PutMapping("/edit")
    fun updateOne(
        @Valid @ModelAttribute("editAccessibilityTag") editAccessibilityTagDTO: AdminAccessibilityTagUpdateRequestDTO,
        bindingResult: BindingResult
    ): String = handleUpdate(
        bindingResult = bindingResult,
        errorFragmentName = "accessibility-tag-modal-form-edit",
        redirectPath = "accessibilityTags"
    ) {
        service.updateOne(editAccessibilityTagDTO)
    }

    @HxRequest
    @DeleteMapping("/delete")
    fun deleteOne(@RequestParam id: UUID): String = handleDelete(
        redirectPath = "accessibilityTags"
    ) {
        service.deleteOne(id)
    }
}