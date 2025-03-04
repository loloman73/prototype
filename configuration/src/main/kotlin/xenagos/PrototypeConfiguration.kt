package xenagos

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import xenagos.domain.service.SortTourListDomainService

@Configuration
class PrototypeConfiguration {

    //Instantiate Domain Services
    @Bean
    fun sortTourListService(): SortTourListDomainService {
        return SortTourListDomainService()
    }

}