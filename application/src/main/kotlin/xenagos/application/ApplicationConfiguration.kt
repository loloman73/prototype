package xenagos.application

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import xenagos.domain.service.SortTourListDomainService

@Configuration
open class ApplicationConfiguration {

    //Instantiate Domain Services
    @Bean
    open fun sortTourListService(): SortTourListDomainService {
        return SortTourListDomainService()
    }

}