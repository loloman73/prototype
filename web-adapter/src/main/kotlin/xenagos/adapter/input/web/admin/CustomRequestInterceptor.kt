package xenagos.adapter.input.web.admin

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

//Problem to solve: When at the HTML form exist unchecked checkbox, the POST request does not contain the checkbox parameter.
//Solution: Intercept the request and append the checkbox parameter if it is not present, with value false.
// Better solution: Apply filter

@Component
class CustomRequestInterceptor : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        val requestMethodIsPOST = request.method.equals("POST", ignoreCase = true)
        val uri = request.requestURI
        val uriIsAdmin = uri.contains("/admin/accessibilityTags/addNew")
        val activeParamIsNull = request.getParameter("active") == null

        if (requestMethodIsPOST && uriIsAdmin && activeParamIsNull) {
            // Forward the request to the same path while appending active=false as a query parameter.
            val dispatcher = request.getRequestDispatcher("$uri?active=false")
            dispatcher.forward(request, response)
            return false
        }
        return true
    }
}