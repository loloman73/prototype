package xenagos.adapter.input.web.admin

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import xenagos.application.port.input.admin.AdminMediaTypesUseCase
import xenagos.application.port.input.admin.model.AdminMediaTypeUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeNewRequestDTO
import java.util.UUID

@Controller
@RequestMapping("/admin/mediaTypes")
class AdminMediaTypesController(private val service: AdminMediaTypesUseCase) : BaseAdminController() {

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("mediaTypes", service.getAll())
        model.addAttribute("addNewMediaType", AdminMediaTypeNewRequestDTO("", false))
        model.addAttribute("editMediaType", AdminMediaTypeUpdateRequestDTO(UUID.randomUUID(), "", false))
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
        service.saveOneNew(addNewMediaTypeDTO)
        return "redirect:htmx:/admin/mediaTypes"
    }

    @HxRequest
    @PutMapping("/edit")
    fun updateOne(
        @Valid @ModelAttribute("editMediaType") editMediaTypeDTO: AdminMediaTypeUpdateRequestDTO,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return "./fragments/admin/media-type-modal-form-edit"
        }
        service.updateOne(editMediaTypeDTO)
        return "redirect:htmx:/admin/mediaTypes"
    }

    @HxRequest
    @DeleteMapping("/delete")
    fun deleteOne(@RequestParam id: UUID): String {
        service.deleteOne(id)
        return "redirect:htmx:/admin/mediaTypes"
    }

    override val fragmentForAddOneNewRequest: String
        get() = TODO("Not yet implemented")
    override val fragmentForUpdateOneRequest: String
        get() = TODO("Not yet implemented")
    override val myPath: String
        get() = TODO("Not yet implemented")

}