package xenagos.adapter.input.web.admin

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import xenagos.application.port.input.admin.model.BaseAdminNewRequestDTO
import xenagos.application.port.input.admin.model.BaseAdminUpdateRequestDTO

// Base controller for admin web adapters to share common helpers.
// Handlers return the fragment, with data errors, if validation fails,
//    otherwise executes the action and return the redirect string to self endpoint.
abstract class BaseAdminController {

    abstract val fragmentForAddOneNewRequest: String
    abstract val fragmentForUpdateOneRequest: String
    abstract val myEndpointPath: String
    abstract val emptyNewRequestDTO: BaseAdminNewRequestDTO
    abstract val emptyUpdateRequestDTO: BaseAdminUpdateRequestDTO

    protected fun handleAddNew(
        bindingResult: BindingResult,
        response: HttpServletResponse,
        saveAction: () -> Unit
    ): String {
        val htmlResponseOrFragment: String

        if (bindingResult.hasErrors()) {
            // Use 422 (Unprocessable Entity) for validation errors so HTMX can swap the returned fragment
            // without triggering generic error handling that can interfere with fragment rendering.
            response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
            htmlResponseOrFragment = "./fragments/admin/$fragmentForAddOneNewRequest"
        } else {
            saveAction()
            response.status = HttpStatus.CREATED.value()
            htmlResponseOrFragment = "redirect:htmx:/admin/$myEndpointPath"
        }
        return htmlResponseOrFragment
    }

    protected fun handleUpdate(bindingResult: BindingResult, saveAction: () -> Unit): String {
        val htmlResponseOrFragment: String
        if (bindingResult.hasErrors()) {
            htmlResponseOrFragment = "./fragments/admin/$fragmentForUpdateOneRequest"
        } else {
            saveAction()
            htmlResponseOrFragment = "redirect:htmx:/admin/$myEndpointPath"
        }
        return htmlResponseOrFragment
    }

    protected fun handleDelete(deleteAction: () -> Unit): String {
        deleteAction()
        return "redirect:htmx:/admin/$myEndpointPath"
    }
}