package xenagos

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import xenagos.adapter.output.persistence.LoadToursForAreaHardEncodedAdapter
import xenagos.application.port.input.GetToursForAreaUseCase
import xenagos.application.service.GetToursForAreaAppService
import xenagos.domain.service.SortTourListDomainService
import xenagos.application.mapper.Mapper

@Configuration
class PrototypeConfiguration {

    //Instantiate Domain Services
    @Bean
    fun sortTourListService(): SortTourListDomainService {
        return SortTourListDomainService()
    }

    //Inject Application Services to Input Ports
    @Bean
    fun getToursForAreaUseCase(): GetToursForAreaUseCase{
        return GetToursForAreaAppService(LoadToursForAreaHardEncodedAdapter(), SortTourListDomainService(), Mapper())
    }


}