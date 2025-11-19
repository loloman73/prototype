package xenagos.adapter.input.web.admin

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
open class WebConfig(private val myPostInterceptor: CustomRequestInterceptor) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        // Apply to the accessibility tags endpoints and its subpaths
        registry.addInterceptor(myPostInterceptor)
            .addPathPatterns("/admin/accessibilityTags/**")
    }
}