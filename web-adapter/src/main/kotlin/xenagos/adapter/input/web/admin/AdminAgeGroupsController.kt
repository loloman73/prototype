package xenagos.adapter.input.web.admin

import jakarta.servlet.http.HttpServletResponse
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
@RequestMapping("/admin/age-groups")
class AdminAgeGroupsController(private val service: AdminAgeGroupUseCase) : BaseAdminController() {

    override val fragmentForAddOneNewRequest: String = "age-group-modal-form-add-new"
    override val fragmentForUpdateOneRequest: String = "age-group-modal-form-edit"
    override val myEndpointPath: String = "age-groups"
    override val emptyNewRequestDTO = AdminAgeGroupNewRequestDTO.createEmptyDeactivated()
    override val emptyUpdateRequestDTO = AdminAgeGroupUpdateRequestDTO.createEmptyDeactivated()

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("listAllModel", service.getAll())
        model.addAttribute("addOneNewModel", emptyNewRequestDTO)
        model.addAttribute("updateOneModel", emptyUpdateRequestDTO)
        return "adminAgeGroups"
    }

    @HxRequest
    @PostMapping
    fun addOneNew(
        @Valid @ModelAttribute("addOneNewModel")
        requestDTO: AdminAgeGroupNewRequestDTO,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): String = handleAddOneNew(bindingResult, response) { service.saveOneNew(requestDTO) }

    @HxRequest
    @PutMapping
    fun updateOne(
        @Valid @ModelAttribute("editAgeGroup")
        requestDTO: AdminAgeGroupUpdateRequestDTO,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): String = handleUpdateOne(bindingResult, response) { service.updateOne(requestDTO) }

    @HxRequest
    @DeleteMapping
    fun deleteOne(@RequestParam id: UUID, response: HttpServletResponse): String =
        handleDeleteOne(response) { service.deleteOne(id) }
}