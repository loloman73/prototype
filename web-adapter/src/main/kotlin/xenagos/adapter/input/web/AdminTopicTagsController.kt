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
class AdminTopicTagsController(private val service: AdminTopicTagsUseCase) {

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("topicTags", service.getAllTopicTags())
        model.addAttribute("addNewTopicTag", AdminTopicTagNewRequestDTO("","", false))
        model.addAttribute("editTopicTag", AdminTopicTagEditRequestDTO(UUID.randomUUID(),"","",false))
        return "adminTopicTags"
    }

    @HxRequest
    @PostMapping("/addNew")
    fun addOneNew(
        @Valid @ModelAttribute("addNewTopicTag") addNewTopicTagDTO: AdminTopicTagNewRequestDTO,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            println(bindingResult.fieldErrors)
            return "topic-tag-modal-form-add-new"
        }
        service.saveNewTopicTag(addNewTopicTagDTO)
        return "redirect:htmx:/admin/topicTags"
    }

    @HxRequest
    @PutMapping("/edit")
    fun updateOne(
        @Valid @ModelAttribute("editTopicTag") editTopicTagDTO: AdminTopicTagEditRequestDTO,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return "topic-tag-modal-form-edit"
        }
        service.updateTopicTag(editTopicTagDTO)
        return "redirect:htmx:/admin/topicTags"
    }

    @HxRequest
    @DeleteMapping("/delete")
    fun deleteOne(@RequestParam id: UUID): String {
        service.deleteTopicTag(id)
        return "redirect:htmx:/admin/topicTags"
    }
}
