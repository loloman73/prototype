package xenagos.adapter.input.web

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import xenagos.application.port.input.AdminTopicTagsUseCase
import xenagos.application.port.input.model.AdminNewTopicTagDTO
import java.util.*

@Controller
@RequestMapping("/admin/topicTags")
class AdminTopicTagsController(private val adminTopicTagsService: AdminTopicTagsUseCase) {

    @GetMapping
    fun showTopicTags(model: Model): String {
        model.addAttribute("topicTags", adminTopicTagsService.getAllTopicTags())
        model.addAttribute("addNewTopicTag", AdminNewTopicTagDTO())
        return "adminTopicTags"
    }

    @HxRequest
    @PostMapping("/addNew")
    fun addNewTopicTag(
        @Valid @ModelAttribute("addNewTopicTag") addNewTopicTagDTO: AdminNewTopicTagDTO,
        bindingResult: BindingResult,
        model: Model
    ): String {

        //Perform validation
        if (bindingResult.hasErrors()) {
            return "/fragments/admin/add-new-topic-tag-modal-form"
        }

        adminTopicTagsService.saveNewTopicTag(addNewTopicTagDTO)
        //Update dB
        return "redirect:htmx:/admin/topicTags"
    }

    @HxRequest
    @DeleteMapping("/delete")
    fun deleteTopicTag(@RequestParam id: UUID): String {
        adminTopicTagsService.deleteTopicTag(id)
        return "redirect:htmx:/admin/topicTags"
    }
}