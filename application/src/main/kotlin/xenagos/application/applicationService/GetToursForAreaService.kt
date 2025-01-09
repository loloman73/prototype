package xenagos.application.applicationService

import org.springframework.stereotype.Service
import xenagos.application.domainService.SortTourListService
import xenagos.application.port.input.model.TourDTO
import xenagos.application.port.input.GetToursForAreaUseCase
import xenagos.application.port.output.LoadToursForAreaOutPort

//TODO -- instantiate class been at Configuration.
// Remove @Service Annotation because this is at the application layer
// and we dont want to have springframework dependencies
@Service
class GetToursForAreaService(private val loadToursForArea: LoadToursForAreaOutPort, private val sortService:SortTourListService): GetToursForAreaUseCase {

    //TODO Fix nullable lists
    override fun whereAreaIs(area: Int): List<TourDTO>? {
        val tourList = loadToursForArea.whereAreaIs(area)
        // TODO -- call all necessary Output adaptors to form a TourDTO
        // TODO -- call mapper class to convert all data to TourDTO

        return sortService.sort(tourList)
    }

}