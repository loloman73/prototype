package xenagos.adapter.input.web.admin

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import xenagos.application.port.input.admin.BaseAdminUseCase
import xenagos.application.port.input.admin.model.BaseAdminNewRequestDTO
import xenagos.application.port.input.admin.model.BaseAdminResponseDTO
import xenagos.application.port.input.admin.model.BaseAdminUpdateRequestDTO
import java.util.UUID

abstract class BaseAdminController<
        TNew : BaseAdminNewRequestDTO,
        TUpdate : BaseAdminUpdateRequestDTO,
        TResponse : BaseAdminResponseDTO>
    (protected val service: BaseAdminUseCase<TNew, TUpdate, TResponse>) {

    abstract val fragmentForAddOneNewRequest: String
    abstract val fragmentForUpdateOneRequest: String
    abstract val myURLEndpoint: String
    abstract val templateName: String
    abstract fun createEmptyNewRequestDTO(): TNew
    abstract fun createEmptyUpdateRequestDTO(): TUpdate

    companion object {
        const val ADMIN_TEMPLATE_PATH_PREFIX = "pages/admin/"
    }

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("listAllModel", service.getAll())
        model.addAttribute("addOneNewModel", createEmptyNewRequestDTO())
        model.addAttribute("updateOneModel", createEmptyUpdateRequestDTO())
        return ADMIN_TEMPLATE_PATH_PREFIX + templateName
    }

    @HxRequest
    @PostMapping
    fun addOneNew(
        @Valid @ModelAttribute("addOneNewModel") requestDTO: TNew,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): String = handleAddOneNew(bindingResult, response) { service.saveOneNew(requestDTO) }

    @HxRequest
    @PutMapping
    fun updateOne(
        @Valid @ModelAttribute("updateOneModel") requestDTO: TUpdate,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): String = handleUpdateOne(bindingResult, response) { service.updateOne(requestDTO) }

    @HxRequest
    @DeleteMapping("/{id}")
    fun deleteOne(
        @PathVariable id: UUID,
        response: HttpServletResponse
    ): String = handleDeleteOne(response) { service.deleteOne(id) }



    private fun handleAddOneNew(
        bindingResult: BindingResult,
        response: HttpServletResponse,
        saveAction: () -> Unit
    ): String {
        val htmlResponseOrFragment: String
        if (bindingResult.hasErrors()) {
            response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
            htmlResponseOrFragment = "fragments/admin/$fragmentForAddOneNewRequest"
        } else {
            saveAction()
            response.status = HttpStatus.CREATED.value()
            htmlResponseOrFragment = "redirect:htmx:/admin/$myURLEndpoint"
        }
        return htmlResponseOrFragment
    }

    private fun handleUpdateOne(
        bindingResult: BindingResult,
        response: HttpServletResponse,
        saveAction: () -> Unit
    ): String {
        val htmlResponseOrFragment: String
        if (bindingResult.hasErrors()) {
            response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
            htmlResponseOrFragment = "fragments/admin/$fragmentForUpdateOneRequest"
        } else {
            saveAction()
            response.status = HttpStatus.OK.value()
            htmlResponseOrFragment = "redirect:htmx:/admin/$myURLEndpoint"
        }
        return htmlResponseOrFragment
    }

    private fun handleDeleteOne(
        response: HttpServletResponse,
        deleteAction: () -> Unit
    ): String {
        deleteAction()
        response.status = HttpStatus.NO_CONTENT.value()
        return "redirect:htmx:/admin/$myURLEndpoint"
    }
}