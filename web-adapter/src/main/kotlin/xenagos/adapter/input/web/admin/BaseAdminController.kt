package xenagos.adapter.input.web.admin

import org.springframework.validation.BindingResult
import xenagos.application.port.input.admin.model.BaseAdminNewRequestDTO
import xenagos.application.port.input.admin.model.BaseAdminUpdateRequestDTO

// Base controller for admin web adapters to share common helpers.
// Handlers return the fragment, with data errors, if validation fails,
//    otherwise executes the action and redirect to self endpoint.
abstract class BaseAdminController {

    abstract val fragmentForAddOneNewRequest: String
    abstract val fragmentForUpdateOneRequest: String
    abstract val myEndpointPath: String

    abstract val emptyNewRequestDTO: BaseAdminNewRequestDTO
    abstract val emptyUpdateRequestDTO: BaseAdminUpdateRequestDTO

    // Builds an HTMX redirect path String to an admin path (adds /admin prefix automatically).
    private fun redirectToAdminHtmx(path: String): String {
        val normalized = if (path.startsWith("/")) path.substring(1) else path
        return "redirect:htmx:/admin/$normalized"
    }

    // Returns fragment, with data errors, if validation fails, otherwise returns null.
    private fun getValidationErrorFragment(bindingResult: BindingResult, fragmentName: String): String? {
        return if (bindingResult.hasErrors()) {
            "./fragments/admin/$fragmentName"
        } else {
            null
        }
    }

    protected fun handleAddNew(bindingResult: BindingResult, saveAction: () -> Unit): String {
        getValidationErrorFragment(bindingResult, fragmentForAddOneNewRequest)?.let { return it }
        saveAction()
        return redirectToAdminHtmx(myEndpointPath)
    }

    protected fun handleUpdate(bindingResult: BindingResult, updateAction: () -> Unit): String {
        getValidationErrorFragment(bindingResult, fragmentForUpdateOneRequest)?.let { return it }
        updateAction()
        return redirectToAdminHtmx(myEndpointPath)
    }

    protected fun handleDelete(deleteAction: () -> Unit): String {
        deleteAction()
        return redirectToAdminHtmx(myEndpointPath)
    }
}