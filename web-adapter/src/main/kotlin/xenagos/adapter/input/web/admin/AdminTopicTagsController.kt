package xenagos.adapter.input.web.admin

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import xenagos.application.port.input.admin.AdminTopicTagsUseCase
import xenagos.application.port.input.admin.model.AdminTopicTagUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminTopicTagNewRequestDTO
import java.util.*

@Controller
@RequestMapping("/admin/topicTags")
class AdminTopicTagsController(private val service: AdminTopicTagsUseCase) : BaseAdminController() {

    override val fragmentForAddOneNewRequest: String = "topic-tag-modal-form-add-new"
    override val fragmentForUpdateOneRequest: String = "topic-tag-modal-form-edit"
    override val myEndpointPath: String = "topicTags"
    override val emptyNewRequestDTO = AdminTopicTagNewRequestDTO("", "", false)
    override val emptyUpdateRequestDTO = AdminTopicTagUpdateRequestDTO(UUID.randomUUID(), "", "", false)

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("listAllModel", service.getAll())
        model.addAttribute("addOneNewModel", emptyNewRequestDTO)
        model.addAttribute("updateOneModel", emptyUpdateRequestDTO)
        return "adminTopicTags"
    }

    @HxRequest
    @PostMapping("/addNew")
    fun addOneNew(
        @Valid @ModelAttribute("addOneNewModel")
        requestDTO: AdminTopicTagNewRequestDTO,
        bindingResult: BindingResult
    ): String = handleAddNew(bindingResult = bindingResult) { service.saveOneNew(requestDTO) }

    @HxRequest
    @PutMapping("/edit")
    fun updateOne(
        @Valid @ModelAttribute("updateOneModel")
        requestDTO: AdminTopicTagUpdateRequestDTO,
        bindingResult: BindingResult
    ): String = handleUpdate(bindingResult = bindingResult) { service.updateOne(requestDTO) }

    @HxRequest
    @DeleteMapping("/delete")
    fun deleteOne(@RequestParam id: UUID): String = handleDelete() { service.deleteOne(id) }
}