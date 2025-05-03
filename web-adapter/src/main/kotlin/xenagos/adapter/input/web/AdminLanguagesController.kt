package xenagos.adapter.input.web

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import xenagos.application.port.input.AdminLanguagesUseCase
import xenagos.application.port.input.model.AdminLanguageEditRequestDTO
import xenagos.application.port.input.model.AdminLanguageNewRequestDTO
import java.util.*

@Controller
@RequestMapping("/admin/languages")
class AdminLanguagesController(private val service: AdminLanguagesUseCase) {

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("languages", service.getAllLanguages())
        model.addAttribute("addNewLanguage", AdminLanguageNewRequestDTO("", "", "", false ))
        model.addAttribute("editLanguage", AdminLanguageEditRequestDTO(UUID.randomUUID(),"","", "", false))
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
            return "/fragments/admin/add-new-language-modal-form"
        }
        service.saveNewLanguage(addNewLanguageDTO)
        return "redirect:htmx:/admin/languages"
    }

    @HxRequest
    @PutMapping("/edit")
    fun updateOne(
        @Valid @ModelAttribute("editLanguage") editLanguageDTO: AdminLanguageEditRequestDTO,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return "/fragments/admin/edit-language-modal-form"
        }
        service.updateLanguage(editLanguageDTO)
        return "redirect:htmx:/admin/languages"
    }

    @HxRequest
    @DeleteMapping("/delete")
    fun deleteOne(@RequestParam id: UUID): String {
        service.deleteLanguage(id)
        return "redirect:htmx:/admin/languages"
    }



}