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
import xenagos.application.port.input.model.AdminTopicTagEditRequestDTO
import java.util.*

@Controller
@RequestMapping("/admin/accessibilityTags")
class AdminAccessibilityTagsController(private val adminAccessibilityTagsService: AdminAccessibilityTagsUseCase) {

    @GetMapping
    fun showAccessibilityTags(model: Model): String {
        model.addAttribute("accessibilityTags", adminAccessibilityTagsService.getAllAccessibilityTags())
        model.addAttribute("addNewAccessibilityTag", AdminAccessibilityTagNewRequestDTO("", "", false))
        model.addAttribute("editAccessibilityTag", AdminAccessibilityTagEditRequestDTO(UUID.randomUUID(), "", "", false))
        return "adminAccessibilityTags"
    }

    @HxRequest
    @PostMapping("/addNew")
    fun addNewAccessibilityTag(
        @Valid @ModelAttribute("addNewAccessibilityTag") addNewAccessibilityTagDTO: AdminAccessibilityTagNewRequestDTO,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return "/fragments/admin/add-new-accessibility-tag-modal-form"
        }
        adminAccessibilityTagsService.saveNewAccessibilityTag(addNewAccessibilityTagDTO)
        return "redirect:htmx:/admin/accessibilityTags"
    }

    @HxRequest
    @PutMapping("/edit")
    fun updateAccessibilityTag(
        @Valid @ModelAttribute("editAccessibilityTag") editAccessibilityTagDTO: AdminAccessibilityTagEditRequestDTO,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return "/fragments/admin/edit-accessibility-tag-modal-form"
        }
        adminAccessibilityTagsService.updateAccessibilityTag(editAccessibilityTagDTO)
        return "redirect:htmx:/admin/accessibilityTags"
    }

    @HxRequest
    @DeleteMapping("/delete")
    fun deleteAccessibilityTag(@RequestParam id: UUID): String {
        adminAccessibilityTagsService.deleteAccessibilityTag(id)
        return "redirect:htmx:/admin/accessibilityTags"
    }
}