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
class AdminAgeGroupsController(private val service: AdminAgeGroupUseCase) : BaseAdminController() {

    override val fragmentForAddOneNewRequest: String = "age-group-modal-form-add-new"
    override val fragmentForUpdateOneRequest: String = "age-group-modal-form-edit"
    override val myEndpointPath: String = "ageGroups"
    override val emptyNewRequestDTO = AdminAgeGroupNewRequestDTO("", 0, 0, false)
    override val emptyUpdateRequestDTO = AdminAgeGroupUpdateRequestDTO(UUID.randomUUID(), "", 0, 0, false)

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("listAllModel", service.getAll())
        model.addAttribute("addOneNewModel",emptyNewRequestDTO )
        model.addAttribute("updateOneModel",emptyUpdateRequestDTO )
        return "adminAgeGroups"
    }

    @HxRequest
    @PostMapping("/addNew")
    fun addOneNew(
        @Valid @ModelAttribute("addOneNewModel")
        requestDTO: AdminAgeGroupNewRequestDTO,
        bindingResult: BindingResult
    ): String = handleAddNew(bindingResult = bindingResult) { service.saveOneNew(requestDTO) }

    @HxRequest
    @PutMapping("/edit")
    fun updateOne(
        @Valid @ModelAttribute("editAgeGroup")
        requestDTO: AdminAgeGroupUpdateRequestDTO,
        bindingResult: BindingResult
    ): String = handleUpdate(bindingResult = bindingResult) { service.updateOne(requestDTO) }

    @HxRequest
    @DeleteMapping("/delete")
    fun deleteOne(@RequestParam id: UUID): String = handleDelete() { service.deleteOne(id) }
}