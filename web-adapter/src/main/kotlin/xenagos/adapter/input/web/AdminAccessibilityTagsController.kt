package xenagos.adapter.input.web

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import xenagos.application.port.input.AdminAccessibilityTagsUseCase
import xenagos.application.port.input.model.AdminAccessibilityTagEditRequestDTO
import xenagos.application.port.input.model.AdminAccessibilityTagNewRequestDTO
import java.util.*

@Controller
@RequestMapping("/admin/accessibilityTags")
class AdminAccessibilityTagsController(private val service: AdminAccessibilityTagsUseCase) {

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("accessibilityTags", service.getAllAccessibilityTags())
        model.addAttribute("addNewAccessibilityTag", AdminAccessibilityTagNewRequestDTO("", "", false))
        model.addAttribute(
            "editAccessibilityTag", AdminAccessibilityTagEditRequestDTO(UUID.randomUUID(), "", "", false)
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
        service.saveNewAccessibilityTag(addNewAccessibilityTagDTO)
        return "redirect:htmx:/admin/accessibilityTags"
    }

    @HxRequest
    @PutMapping("/edit")
    fun updateOne(
        @Valid @ModelAttribute("editAccessibilityTag") editAccessibilityTagDTO: AdminAccessibilityTagEditRequestDTO,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return "./fragments/admin/accessibility-tag-modal-form-edit"
        }
        service.updateAccessibilityTag(editAccessibilityTagDTO)
        return "redirect:htmx:/admin/accessibilityTags"
    }

    //TODO: in case there is error in deletion -> return feedback
    @HxRequest
    @DeleteMapping("/delete")
    fun deleteOne(@RequestParam id: UUID): String {
        service.deleteAccessibilityTag(id)
        return "redirect:htmx:/admin/accessibilityTags"
    }
}