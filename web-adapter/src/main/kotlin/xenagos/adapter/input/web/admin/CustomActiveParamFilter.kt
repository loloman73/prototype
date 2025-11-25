package xenagos.adapter.input.web.admin

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

/**
 * Ensures that when submitting the add-new form via HTMX and the checkbox "active" is unchecked
 * (thus missing from the form body), the request still contains active=false so validation/binding
 * works and the controller can return the final response (201 + HX-Redirect) without using a forward.
 */
@Component
class CustomActiveParamFilter : OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val isPost = request.method.equals("POST", ignoreCase = true)
        val uri = request.requestURI ?: ""
        val pathMatches = uri.contains("/admin/accessibilityTags/addNew")
        val activeMissing = request.getParameter("active") == null
        return !(isPost && pathMatches && activeMissing)
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // Wrap the request to inject active=false
        val wrapped = object : HttpServletRequestWrapper(request) {
            private val additionalParams: Map<String, Array<String>> = mapOf("active" to arrayOf("false"))

            override fun getParameter(name: String): String? {
                if (additionalParams.containsKey(name)) {
                    return additionalParams[name]?.firstOrNull()
                }
                return super.getParameter(name)
            }

            override fun getParameterValues(name: String): Array<String>? {
                if (additionalParams.containsKey(name)) {
                    return additionalParams[name]
                }
                return super.getParameterValues(name)
            }

            override fun getParameterMap(): MutableMap<String, Array<String>> {
                val map = super.getParameterMap().toMutableMap()
                // don't overwrite if already present (paranoia, though shouldNotFilter prevents that)
                additionalParams.forEach { (k, v) -> map.putIfAbsent(k, v) }
                return map
            }
        }
        filterChain.doFilter(wrapped, response)
    }
}
