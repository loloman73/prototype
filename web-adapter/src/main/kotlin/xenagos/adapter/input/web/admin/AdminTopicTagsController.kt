package xenagos.adapter.input.web.admin

import jakarta.servlet.http.HttpServletResponse
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
@RequestMapping("/admin/topic-tags")
class AdminTopicTagsController(private val service: AdminTopicTagsUseCase) : BaseAdminController() {

    override val fragmentForAddOneNewRequest: String = "topic-tag-modal-form-add-new"
    override val fragmentForUpdateOneRequest: String = "topic-tag-modal-form-edit"
    override val myURLEndpoint: String = "topic-tags"
    override val emptyNewRequestDTO = AdminTopicTagNewRequestDTO.createEmptyDeactivated()
    override val emptyUpdateRequestDTO = AdminTopicTagUpdateRequestDTO.createEmptyDeactivated()

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("listAllModel", service.getAll())
        model.addAttribute("addOneNewModel", emptyNewRequestDTO)
        model.addAttribute("updateOneModel", emptyUpdateRequestDTO)
        return ADMIN_TEMPLATE_PATH_PREFIX + "adminTopicTags"
    }

    @HxRequest
    @PostMapping
    fun addOneNew(
        @Valid @ModelAttribute("addOneNewModel")
        requestDTO: AdminTopicTagNewRequestDTO,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): String = handleAddOneNew(bindingResult, response) { service.saveOneNew(requestDTO) }

    @HxRequest
    @PutMapping
    fun updateOne(
        @Valid @ModelAttribute("updateOneModel")
        requestDTO: AdminTopicTagUpdateRequestDTO,
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