package xenagos.adapter.input.web.admin

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import xenagos.application.port.input.admin.AdminMediaTypesUseCase
import xenagos.application.port.input.admin.model.AdminMediaTypeUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeNewRequestDTO
import java.util.UUID

@Controller
@RequestMapping("/admin/mediaTypes")
class AdminMediaTypesController(private val service: AdminMediaTypesUseCase) : BaseAdminController() {

    override val fragmentForAddOneNewRequest: String = "topic-tag-modal-form-add-new"
    override val fragmentForUpdateOneRequest: String = "media-type-modal-form-edit"
    override val myEndpointPath: String = "mediaTypes"
    override val emptyNewRequestDTO = AdminMediaTypeNewRequestDTO.createEmptyDeactivated()
    override val emptyUpdateRequestDTO = AdminMediaTypeUpdateRequestDTO.createEmptyDeactivated()

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("listAllModel", service.getAll())
        model.addAttribute("addOneNewModel", emptyNewRequestDTO)
        model.addAttribute("updateOneModel", emptyUpdateRequestDTO)
        return "adminMediaTypes"
    }

    @HxRequest
    @PostMapping("/addNew")
    fun addOneNew(
        @Valid @ModelAttribute("addOneNewModel")
        requestDTO: AdminMediaTypeNewRequestDTO,
        bindingResult: BindingResult
    ): String = handleAddNew(bindingResult = bindingResult) { service.saveOneNew(requestDTO) }

    @HxRequest
    @PutMapping("/edit")
    fun updateOne(
        @Valid @ModelAttribute("updateOneModel")
        requestDTO: AdminMediaTypeUpdateRequestDTO,
        bindingResult: BindingResult
    ): String = handleUpdate(bindingResult = bindingResult) { service.updateOne(requestDTO) }

    @HxRequest
    @DeleteMapping("/delete")
    fun deleteOne(@RequestParam id: UUID): String = handleDelete() { service.deleteOne(id) }
}