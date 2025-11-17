package xenagos.adapter.input.web.admin

import org.springframework.validation.BindingResult

// Base controller for admin web adapters to share common helpers.
abstract class BaseAdminController {

    // Builds an HTMX redirect to an admin path (adds /admin prefix automatically).
    protected fun redirectToAdminHtmx(path: String): String {
        val normalized = if (path.startsWith("/")) path.substring(1) else path
        return "redirect:htmx:/admin/$normalized"
    }

    // Builds a fragment path for admin fragments.
    protected fun adminFragment(fragmentName: String): String =
        "./fragments/admin/$fragmentName"

    // Returns the admin fragment if validation errors exist, otherwise null.
    // Combines adminFragment() and returnFragmentOnErrors() for convenience.
    protected fun returnAdminFragmentOnErrors(bindingResult: BindingResult, fragmentName: String): String? =
        if (bindingResult.hasErrors()) adminFragment(fragmentName) else null

    // Generic handler for adding a new entity with validation.
    // Returns error fragment if validation fails, otherwise executes the save action and redirects.
    //
    // @param bindingResult validation result from Spring
    // @param errorFragmentName fragment to show on validation errors (without path prefix)
    // @param redirectPath path to redirect to after successful save (without /admin prefix)
    // @param saveAction lambda that performs the actual save operation
    // @return view name (either error fragment or redirect)
    protected fun handleAddNew(
        bindingResult: BindingResult,
        errorFragmentName: String,
        redirectPath: String,
        saveAction: () -> Unit
    ): String {
        if (bindingResult.hasErrors()) {
            return adminFragment(errorFragmentName)
        }
        saveAction()
        return redirectToAdminHtmx(redirectPath)
    }

    // Generic handler for updating an entity with validation.
    // Returns error fragment if validation fails, otherwise executes the update action and redirects.
    //
    // @param bindingResult validation result from Spring
    // @param errorFragmentName fragment to show on validation errors (without path prefix)
    // @param redirectPath path to redirect to after successful update (without /admin prefix)
    // @param updateAction lambda that performs the actual update operation
    // @return view name (either error fragment or redirect)
    protected fun handleUpdate(
        bindingResult: BindingResult,
        errorFragmentName: String,
        redirectPath: String,
        updateAction: () -> Unit
    ): String {
        if (bindingResult.hasErrors()) {
            return adminFragment(errorFragmentName)
        }
        updateAction()
        return redirectToAdminHtmx(redirectPath)
    }

    // Generic handler for deleting an entity.
    // Executes the delete action and redirects.
    //
    // @param redirectPath path to redirect to after successful delete (without /admin prefix)
    // @param deleteAction lambda that performs the actual delete operation
    // @return redirect view name
    protected fun handleDelete(
        redirectPath: String,
        deleteAction: () -> Unit
    ): String {
        deleteAction()
        return redirectToAdminHtmx(redirectPath)
    }
}