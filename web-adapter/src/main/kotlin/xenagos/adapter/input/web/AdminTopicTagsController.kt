package xenagos.adapter.input.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import xenagos.application.port.input.AdminTopicTagsUseCase

@Controller
@RequestMapping("/admin/topicTags")
class AdminTopicTagsController(private val adminTopicTagsService: AdminTopicTagsUseCase) {

    @GetMapping
    fun showTopicTags(model: Model): String{
        model.addAttribute("topicTags", adminTopicTagsService.getAllTopicTags())
        return "adminTopicTags"
    }


}