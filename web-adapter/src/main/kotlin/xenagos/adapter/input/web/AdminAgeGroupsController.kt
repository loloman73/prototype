package xenagos.adapter.input.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import xenagos.application.port.input.AdminAgeGroupUseCase

@Controller
@RequestMapping("/admin/ageGroups")
class AdminAgeGroupsController(private val adminAgeGroupsService: AdminAgeGroupUseCase) {

    @GetMapping
    fun showAgeGroups(model: Model): String {
        model.addAttribute("ageGroups", adminAgeGroupsService.getAllAgeGroups())
        return "adminAgeGroups"
    }
}