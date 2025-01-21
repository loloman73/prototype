package xenagos

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import xenagos.adapter.output.persistence.AdminTopicTagsPersistence
import xenagos.adapter.output.persistence.AdminTopicTagsRepository
import xenagos.adapter.output.persistence.LoadToursForAreaHardEncodedAdapter
import xenagos.application.port.input.GetToursForAreaUseCase
import xenagos.application.service.GetToursForAreaAppService
import xenagos.domain.service.SortTourListDomainService
import xenagos.application.mapper.AdminTopicTagMapper
import xenagos.application.port.input.AdminTopicTagsUseCase
import xenagos.application.port.output.AdminTopicTagsOutputPort
import xenagos.application.service.AdminTopicTagsService

@Configuration
class PrototypeConfiguration {

    //Instantiate Domain Services
    @Bean
    fun sortTourListService(): SortTourListDomainService {
        return SortTourListDomainService()
    }

    //Inject Application Services to Input Ports
//    @Bean
//    fun getToursForAreaUseCase(): GetToursForAreaUseCase{
//        return GetToursForAreaAppService(LoadToursForAreaHardEncodedAdapter(), SortTourListDomainService(), AdminTopicTagMapper())
//    }

//    @Bean
//    fun adminTopicTagsUseCase(): AdminTopicTagsUseCase{
//        return AdminTopicTagsService( AdminTopicTagsOutputPort)
//    }
//    @Bean
//    fun adminTopicTagsOutputPort(): AdminTopicTagsOutputPort {
//        return AdminTopicTagsPersistence(AdminTopicTagsRepository() )
//    }

}