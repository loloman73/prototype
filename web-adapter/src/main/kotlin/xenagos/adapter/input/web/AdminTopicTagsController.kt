package xenagos.adapter.input.web

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import xenagos.application.port.input.AdminTopicTagsUseCase
import xenagos.application.port.input.model.AdminTopicTagEditRequestDTO
import xenagos.application.port.input.model.AdminTopicTagNewRequestDTO
import java.util.*

@Controller
@RequestMapping("/admin/topicTags")
class AdminTopicTagsController(private val adminTopicTagsService: AdminTopicTagsUseCase) {

    @GetMapping
    fun showTopicTags(model: Model): String {
        model.addAttribute("topicTags", adminTopicTagsService.getAllTopicTags())
        model.addAttribute("addNewTopicTag", AdminTopicTagNewRequestDTO("","", false))
        model.addAttribute("editTopicTag", AdminTopicTagEditRequestDTO(UUID.randomUUID(),"","",false))
        return "adminTopicTags"
    }

    @HxRequest
    @PostMapping("/addNew")
    fun addNewTopicTag(
        @Valid @ModelAttribute("addNewTopicTag") addNewTopicTagDTO: AdminTopicTagNewRequestDTO,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return "/fragments/admin/add-new-topic-tag-modal-form"
        }
        adminTopicTagsService.saveNewTopicTag(addNewTopicTagDTO)
        return "redirect:htmx:/admin/topicTags"
    }

    @HxRequest
    @PutMapping("/edit")
    fun updateTopicTag(
        @Valid @ModelAttribute("editTopicTag") editTopicTagDTO: AdminTopicTagEditRequestDTO,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return "/fragments/admin/edit-topic-tag-modal-form"
        }
        adminTopicTagsService.updateTopicTag(editTopicTagDTO)
        return "redirect:htmx:/admin/topicTags"
    }

    @HxRequest
    @DeleteMapping("/delete")
    fun deleteTopicTag(@RequestParam id: UUID): String {
        adminTopicTagsService.deleteTopicTag(id)
        return "redirect:htmx:/admin/topicTags"
    }
}
