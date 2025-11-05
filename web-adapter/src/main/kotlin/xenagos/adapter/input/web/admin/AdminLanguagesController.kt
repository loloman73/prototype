package xenagos.adapter.input.web.admin

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import xenagos.application.port.input.admin.AdminLanguagesUseCase
import xenagos.application.port.input.admin.model.AdminLanguageUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminLanguageNewRequestDTO
import java.util.*

@Controller
@RequestMapping("/admin/languages")
class AdminLanguagesController(private val service: AdminLanguagesUseCase) {

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("languages", service.getAll())
        model.addAttribute("addNewLanguage", AdminLanguageNewRequestDTO("", "", "", false ))
        model.addAttribute("editLanguage", AdminLanguageUpdateRequestDTO(UUID.randomUUID(),"","", "", false))
        return "adminLanguages"
    }

    @HxRequest
    @PostMapping("/addNew")
    fun addOneNew(
        @Valid @ModelAttribute("addNewLanguage") addNewLanguageDTO: AdminLanguageNewRequestDTO,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            println(bindingResult.fieldErrors)
            return "./fragments/admin/language-modal-form-add-new"
        }
        service.saveOneNew(addNewLanguageDTO)
        return "redirect:htmx:/admin/languages"
    }

    @HxRequest
    @PutMapping("/edit")
    fun updateOne(
        @Valid @ModelAttribute("editLanguage") editLanguageDTO: AdminLanguageUpdateRequestDTO,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return "./fragments/admin/language-modal-form-edit"
        }
        service.updateOne(editLanguageDTO)
        return "redirect:htmx:/admin/languages"
    }

    @HxRequest
    @DeleteMapping("/delete")
    fun deleteOne(@RequestParam id: UUID): String {
        service.deleteOne(id)
        return "redirect:htmx:/admin/languages"
    }
}