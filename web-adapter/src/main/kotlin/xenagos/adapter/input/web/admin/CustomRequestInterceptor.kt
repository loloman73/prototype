package xenagos.adapter.input.web.admin

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class CustomRequestInterceptor: HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // Only handle POST requests for AdminAccessibilityTagsController endpoints
        if (request.method.equals("POST", ignoreCase = true)) {
            val uri = request.requestURI ?: ""
            // Specifically handle addNew on accessibility tags
            if (uri == "/admin/accessibilityTags/addNew") {
                val activeParam = request.getParameter("active")
                if (activeParam == null || activeParam.isBlank()) {
                    // Forward the request to the same path while appending active=false as a query parameter.
                    // Forward keeps it a POST and preserves original form parameters; query param will be merged.
                    val dispatcher = request.getRequestDispatcher("$uri?active=false")
                    dispatcher.forward(request, response)
                    return false
                }
            }
        }
        return true
    }
}