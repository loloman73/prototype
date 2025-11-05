package xenagos.adapter.input.web.admin

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import xenagos.application.port.input.admin.AdminAgeGroupUseCase
import xenagos.application.port.input.admin.model.AdminAgeGroupUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupNewRequestDTO
import java.util.*

@Controller
@RequestMapping("/admin/ageGroups")
class AdminAgeGroupsController(private val service: AdminAgeGroupUseCase) {

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("ageGroups", service.getAll())
        model.addAttribute("addNewAgeGroup", AdminAgeGroupNewRequestDTO("",0, 0,false))
        model.addAttribute("editAgeGroup", AdminAgeGroupUpdateRequestDTO(UUID.randomUUID(),"",0, 0,false))
        return "adminAgeGroups"
    }

    @HxRequest
    @PostMapping("/addNew")
    fun addOneNew(
        @Valid @ModelAttribute("addNewAgeGroup") addNewAgeGroupDTO: AdminAgeGroupNewRequestDTO,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return "./fragments/admin/age-group-modal-form-add-new"
        }
        service.saveOneNew(addNewAgeGroupDTO)
        return "redirect:htmx:/admin/ageGroups"
    }

    @HxRequest
    @PutMapping("/edit")
    fun updateOne(
        @Valid @ModelAttribute("editAgeGroup") editAgeGroupDTO: AdminAgeGroupUpdateRequestDTO,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return "./fragments/admin/age-group-modal-form-edit"
        }
        service.updateOne(editAgeGroupDTO)
        return "redirect:htmx:/admin/ageGroups"
    }

    @HxRequest
    @DeleteMapping("/delete")
    fun deleteOne(@RequestParam id: UUID): String {
        service.deleteOne(id)
        return "redirect:htmx:/admin/ageGroups"
    }
}