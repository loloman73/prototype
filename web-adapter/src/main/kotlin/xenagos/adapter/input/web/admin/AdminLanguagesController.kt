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
class AdminLanguagesController(private val service: AdminLanguagesUseCase) : BaseAdminController() {

    override val fragmentForAddOneNewRequest = "language-modal-form-add-new"
    override val fragmentForUpdateOneRequest = "language-modal-form-edit"
    override val myEndpointPath: String = "languages"
    override val emptyNewRequestDTO = AdminLanguageNewRequestDTO("", "", "", false)
    override val emptyUpdateRequestDTO = AdminLanguageUpdateRequestDTO(UUID.randomUUID(), "", "", "", false)

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("listAllModel", service.getAll())
        model.addAttribute("addOneNewModel", emptyNewRequestDTO)
        model.addAttribute("updateOneModel", emptyUpdateRequestDTO)
        return "adminLanguages"
    }

    @HxRequest
    @PostMapping("/addNew")
    fun addOneNew(
        @Valid @ModelAttribute("addOneNewModel")
        requestDTO: AdminLanguageNewRequestDTO,
        bindingResult: BindingResult
    ): String = handleAddNew(bindingResult = bindingResult) { service.saveOneNew(requestDTO) }

    @HxRequest
    @PutMapping("/edit")
    fun updateOne(
        @Valid @ModelAttribute("updateOneModel")
        requestDTO: AdminLanguageUpdateRequestDTO,
        bindingResult: BindingResult
    ): String = handleUpdate(bindingResult = bindingResult) { service.updateOne(requestDTO) }

    @HxRequest
    @DeleteMapping("/delete")
    fun deleteOne(@RequestParam id: UUID): String = handleDelete() { service.deleteOne(id) }
}