package xenagos.adapter.input.web

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class ThymeleafLayoutConfiguration {

    @Bean
    open fun layoutDialect(): LayoutDialect {
        return LayoutDialect()
    }

}