package xenagos.adapter.input.web.admin

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import xenagos.application.port.input.admin.AdminDurationTypesUseCase
import xenagos.application.port.input.admin.model.AdminDurationTypeNewRequestDTO
import xenagos.application.port.input.admin.model.AdminDurationTypeUpdateRequestDTO
import java.util.UUID

@Controller
@RequestMapping("/admin/duration-types")
class AdminDurationTypesController(private val service: AdminDurationTypesUseCase) : BaseAdminController() {

    override val fragmentForAddOneNewRequest: String = "duration-type-modal-form-add-new"
    override val fragmentForUpdateOneRequest: String = "duration-type-modal-form-edit"
    override val myEndpointPath: String = "duration-types"
    override val emptyNewRequestDTO = AdminDurationTypeNewRequestDTO.createEmptyDeactivated()
    override val emptyUpdateRequestDTO = AdminDurationTypeUpdateRequestDTO.createEmptyDeactivated()

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("listAllModel", service.getAll())
        model.addAttribute("addOneNewModel", emptyNewRequestDTO)
        model.addAttribute("updateOneModel", emptyUpdateRequestDTO)
        return "adminDurationTypes"
    }

    @HxRequest
    @PostMapping
    fun addOneNew(
        @Valid @ModelAttribute("addOneNewModel")
        requestDTO: AdminDurationTypeNewRequestDTO,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): String = handleAddOneNew(bindingResult, response) { service.saveOneNew(requestDTO) }

    @HxRequest
    @PutMapping
    fun updateOne(
        @Valid @ModelAttribute("updateOneModel")
        requestDTO: AdminDurationTypeUpdateRequestDTO,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): String = handleUpdateOne(bindingResult, response) { service.updateOne(requestDTO) }

    @HxRequest
    @DeleteMapping("/{id}")
    fun deleteOne(
        @PathVariable id: UUID,
        response: HttpServletResponse
    ): String = handleDeleteOne(response) { service.deleteOne(id) }
}