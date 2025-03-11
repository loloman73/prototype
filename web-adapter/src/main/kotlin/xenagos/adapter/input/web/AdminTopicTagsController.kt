package xenagos.adapter.input.web

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

    @PostMapping("/addNew")
    fun addNewTopicTag(): String {

//        perform validation
//        return "/fragments/admin/add-new-topic-tag-modal-body"
        return "redirect:/admin/topicTags"
    }

}