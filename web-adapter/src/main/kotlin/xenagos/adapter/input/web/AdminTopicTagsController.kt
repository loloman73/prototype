package xenagos.adapter.input.web

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import xenagos.application.port.input.AdminTopicTagsUseCase

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
    fun addNewTopicTag(response: HttpServletResponse): String  {

//        perform validation
//        return "/fragments/admin/add-new-topic-tag-modal-body"

        return  "redirect:htmx:/admin/topicTags"
    }

}