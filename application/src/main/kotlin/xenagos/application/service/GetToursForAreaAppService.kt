package xenagos.application.service

import org.springframework.stereotype.Component
import xenagos.application.mapper.TourMapper
import xenagos.domain.service.SortTourListDomainService
import xenagos.domain.model.*
import xenagos.application.port.input.model.TourDTO
import xenagos.application.port.input.GetToursForAreaUseCase
import xenagos.application.port.output.LoadToursForAreaOutPort

@Component
class GetToursForAreaAppService(
    private val loadToursForArea: LoadToursForAreaOutPort,
    private val sortService: SortTourListDomainService,
    private val tourMapper: TourMapper
) : GetToursForAreaUseCase {

    //TODO check for nullability of lists
    override fun whereAreaIs(area: Int): List<TourDTO> {
        val sortedTourDTOList = mutableListOf<TourDTO>()
        val tourList: Set<Tour> = loadToursForArea.whereAreaIs(area)!!

        //TODO call Domain Service to add sort logic
        //TODO call Domain Service to exclude Tours

        tourList.forEach{
            sortedTourDTOList.add(tourMapper.entityToDto(it))
        }

        return sortedTourDTOList
    }

}