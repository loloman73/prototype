package xenagos.adapter.input.web

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import xenagos.application.port.input.AdminAgeGroupUseCase
import xenagos.application.port.input.model.AdminAgeGroupEditRequestDTO
import xenagos.application.port.input.model.AdminAgeGroupNewRequestDTO
import java.util.*

@Controller
@RequestMapping("/admin/ageGroups")
class AdminAgeGroupsController(private val service: AdminAgeGroupUseCase) {

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("ageGroups", service.getAllAgeGroups())
        model.addAttribute("addNewAgeGroup", AdminAgeGroupNewRequestDTO("",0, 0,false))
        model.addAttribute("editAgeGroup", AdminAgeGroupEditRequestDTO(UUID.randomUUID(),"",0, 0,false))
        return "adminAgeGroups"
    }

    @HxRequest
    @PostMapping("/addNew")
    fun addOneNew(
        @Valid @ModelAttribute("addNewAgeGroup") addNewAgeGroupDTO: AdminAgeGroupNewRequestDTO,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return "age-group-modal-form-add-new"
        }
        service.saveNewAgeGroup(addNewAgeGroupDTO)
        return "redirect:htmx:/admin/ageGroups"
    }

    @HxRequest
    @PutMapping("/edit")
    fun updateOne(
        @Valid @ModelAttribute("editAgeGroup") editAgeGroupDTO: AdminAgeGroupEditRequestDTO,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return "age-group-modal-form-edit"
        }
        service.updateAgeGroup(editAgeGroupDTO)
        return "redirect:htmx:/admin/ageGroups"
    }

    @HxRequest
    @DeleteMapping("/delete")
    fun deleteOne(@RequestParam id: UUID): String {
        service.deleteAgeGroup(id)
        return "redirect:htmx:/admin/ageGroups"
    }
}