package xenagos.adapter.input.web.admin

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

    protected fun handleAddNew(bindingResult: BindingResult, saveAction: () -> Unit): ResponseEntity<String> {
        val htmlResponseOrFragment: String =""
        if (bindingResult.hasErrors()) {
            htmlResponseOrFragment = "./fragments/admin/$fragmentForAddOneNewRequest"

        } else {
            saveAction()
            val headers = HttpHeaders()
            headers.set("HX-Redirect", "/admin/$myEndpointPath")
            return ResponseEntity(headers, HttpStatus.OK)
        }
    }

    protected fun handleUpdate(bindingResult: BindingResult, saveAction: () -> Unit): String {
        val htmlResponseOrFragment: String
        if (bindingResult.hasErrors()) {
            htmlResponseOrFragment =  "./fragments/admin/$fragmentForUpdateOneRequest"
        } else {
            saveAction()
            htmlResponseOrFragment = "redirect:htmx:/admin/$myEndpointPath"
        }

    }

    protected fun handleDelete(deleteAction: () -> Unit): String {
        deleteAction()
        return "redirect:htmx:/admin/$myEndpointPath"
    }

}