package xenagos.application.service

import xenagos.domain.service.SortTourListDomainService
import xenagos.domain.model.*
import xenagos.application.port.input.model.TourDTO
import xenagos.application.port.input.GetToursForAreaUseCase
import xenagos.application.port.output.LoadToursForAreaOutPort
import xenagos.application.mapper.Mapper
import java.util.*

class GetToursForAreaAppService(
    private val loadToursForArea: LoadToursForAreaOutPort,
    private val sortService: SortTourListDomainService,
    private val mapper: Mapper
) : GetToursForAreaUseCase {

    //TODO Fix nullable lists
    override fun whereAreaIs(area: Int): List<TourDTO> {
        val sortedTourDTOList = mutableListOf<TourDTO>()
        val tourList: Set<Tour> = loadToursForArea.whereAreaIs(area)!!
        //TODO call Domain Service to add sort logic

        return sortedTourDTOList
    }

}