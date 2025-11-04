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
class AdminAccessibilityTagsController(private val service: AdminAccessibilityTagsUseCase) {

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("accessibilityTags", service.getAll())
        model.addAttribute("addNewAccessibilityTag", AdminAccessibilityTagNewRequestDTO("", "", false))
        model.addAttribute(
            "editAccessibilityTag", AdminAccessibilityTagUpdateRequestDTO(UUID.randomUUID(), "", "", false)
        )
        return "adminAccessibilityTags"
    }

    @HxRequest
    @PostMapping("/addNew")
    fun addOneNew(
        @Valid @ModelAttribute("addNewAccessibilityTag") addNewAccessibilityTagDTO: AdminAccessibilityTagNewRequestDTO,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return "./fragments/admin/accessibility-tag-modal-form-add-new"
        }
        service.saveOneNew(addNewAccessibilityTagDTO)
        return "redirect:htmx:/admin/accessibilityTags"
    }

    @HxRequest
    @PutMapping("/edit")
    fun updateOne(
        @Valid @ModelAttribute("editAccessibilityTag") editAccessibilityTagDTO: AdminAccessibilityTagUpdateRequestDTO,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return "./fragments/admin/accessibility-tag-modal-form-edit"
        }
        service.updateOne(editAccessibilityTagDTO)
        return "redirect:htmx:/admin/accessibilityTags"
    }

    //TODO: in case there is error in deletion -> return feedback
    @HxRequest
    @DeleteMapping("/delete")
    fun deleteOne(@RequestParam id: UUID): String {
        service.deleteOne(id)
        return "redirect:htmx:/admin/accessibilityTags"
    }
}