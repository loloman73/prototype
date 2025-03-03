package xenagos.adapter.input.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import xenagos.application.port.input.AdminAccessibilityTagsUseCase

@Controller
@RequestMapping("/admin/accessibilityTags")
class AdminAccessibilityTagsController(private val adminAccessibilityTagsService: AdminAccessibilityTagsUseCase) {
    @GetMapping
    fun showAccessibilityTags(model: Model): String {
        model.addAttribute("accesibilityTags", adminAccessibilityTagsService.getAllAccessibilityTags())
        return "adminAccessibilityTags"
    }
}