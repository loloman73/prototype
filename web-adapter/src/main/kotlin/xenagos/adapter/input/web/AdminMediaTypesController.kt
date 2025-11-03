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
import xenagos.application.port.input.AdminMediaTypesUseCase
import xenagos.application.port.input.model.AdminMediaTypeEditRequestDTO
import xenagos.application.port.input.model.AdminMediaTypeNewRequestDTO
import java.util.UUID

@Controller
@RequestMapping("/admin/mediaTypes")
class AdminMediaTypesController(private val service: AdminMediaTypesUseCase) {

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("mediaTypes", service.getAllMediaTypes())
        model.addAttribute("addNewMediaType", AdminMediaTypeNewRequestDTO("", false))
        model.addAttribute("editMediaType", AdminMediaTypeEditRequestDTO(UUID.randomUUID(), "", false))
        return "adminMediaTypes"
    }

    @HxRequest
    @PostMapping("/addNew")
    fun addOneNew(
        @Valid @ModelAttribute("addNewMediaType") addNewMediaTypeDTO: AdminMediaTypeNewRequestDTO,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return "./fragments/admin/media-type-modal-form-add-new"
        }
        service.saveNewMediaType(addNewMediaTypeDTO)
        return "redirect:htmx:/admin/mediaTypes"
    }

}