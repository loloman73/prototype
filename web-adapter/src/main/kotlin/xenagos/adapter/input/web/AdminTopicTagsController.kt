package xenagos.adapter.input.web

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import xenagos.application.port.input.AdminTopicTagsUseCase
import xenagos.application.port.input.model.AdminTopicTagDTO

@Controller
@RequestMapping("/admin/topicTags")
class AdminTopicTagsController(private val adminTopicTagsService: AdminTopicTagsUseCase) {

    @GetMapping
    fun showTopicTags(model: Model): String {
        model.addAttribute("topicTags", adminTopicTagsService.getAllTopicTags())
        return "adminTopicTags"
    }

    @HxRequest
    @PostMapping("/addNew")
    fun addNewTopicTag(
        @Valid @ModelAttribute("topicTag") formData: AdminTopicTagDTO,
        bindingResult: BindingResult
    ): String {

        //Perform validation
        if (bindingResult.hasErrors()) {
            return "fragments/admin/add-new-topic-tag-modal-body"
        }

        //Update dB
        return "redirect:htmx:/admin/topicTags"
    }

}