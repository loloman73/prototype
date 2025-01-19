package xenagos.adapter.input.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import xenagos.application.port.input.AdminGetTopicsUseCase

@Controller
@RequestMapping("/admin/topicTags")
class AdminTopicTagsController(private val getTopics: AdminGetTopicsUseCase) {

    @GetMapping
    fun topicTags(model: Model): String{


        model.addAttribute("topics","mytopic")
        return "adminTopicTags"
    }

}