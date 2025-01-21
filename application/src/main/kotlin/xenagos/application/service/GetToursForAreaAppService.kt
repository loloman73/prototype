package xenagos.application.service

import org.springframework.stereotype.Component
import xenagos.domain.service.SortTourListDomainService
import xenagos.domain.model.*
import xenagos.application.port.input.model.TourDTO
import xenagos.application.port.input.GetToursForAreaUseCase
import xenagos.application.port.output.LoadToursForAreaOutPort
import xenagos.application.mapper.AdminTopicTagMapper

@Component
class GetToursForAreaAppService(
    private val loadToursForArea: LoadToursForAreaOutPort,
    private val sortService: SortTourListDomainService
) : GetToursForAreaUseCase {

    //TODO Fix nullable lists
    override fun whereAreaIs(area: Int): List<TourDTO> {
        val sortedTourDTOList = mutableListOf<TourDTO>()
        val tourList: Set<Tour> = loadToursForArea.whereAreaIs(area)!!
        //TODO call Domain Service to add sort logic

        return sortedTourDTOList
    }

}