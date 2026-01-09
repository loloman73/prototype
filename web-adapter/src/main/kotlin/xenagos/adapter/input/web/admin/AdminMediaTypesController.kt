package xenagos.adapter.input.web.admin

import jakarta.servlet.http.HttpServletResponse
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import xenagos.application.port.input.admin.AdminMediaTypesUseCase
import xenagos.application.port.input.admin.model.AdminMediaTypeUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeNewRequestDTO
import java.util.UUID

@Controller
@RequestMapping("/admin/media-types")
class AdminMediaTypesController(private val service: AdminMediaTypesUseCase) : BaseAdminController() {

    override val fragmentForAddOneNewRequest: String = "media-type-modal-form-add-new"
    override val fragmentForUpdateOneRequest: String = "media-type-modal-form-edit"
    override val myURLEndpoint: String = "media-types"
    override val emptyNewRequestDTO = AdminMediaTypeNewRequestDTO.createEmptyDeactivated()
    override val emptyUpdateRequestDTO = AdminMediaTypeUpdateRequestDTO.createEmptyDeactivated()

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("listAllModel", service.getAll())
        model.addAttribute("addOneNewModel", emptyNewRequestDTO)
        model.addAttribute("updateOneModel", emptyUpdateRequestDTO)
        return pathPrefix + "adminMediaTypes"
    }

    @HxRequest
    @PostMapping
    fun addOneNew(
        @Valid @ModelAttribute("addOneNewModel")
        requestDTO: AdminMediaTypeNewRequestDTO,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): String = handleAddOneNew(bindingResult, response) { service.saveOneNew(requestDTO) }

    @HxRequest
    @PutMapping
    fun updateOne(
        @Valid @ModelAttribute("updateOneModel")
        requestDTO: AdminMediaTypeUpdateRequestDTO,
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