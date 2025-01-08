package xenagos.application.appService

import org.springframework.stereotype.Service
import xenagos.application.port.input.model.TourDTO
import xenagos.application.port.input.GetToursForAreaUseCase
import xenagos.application.port.output.LoadToursForAreaOutPort

//TODO -- instantiate class been at Configuration.
// Remove @Service Annotation because this is at the application layer
// and we dont want to have springframework dependencies
@Service
class GetToursForAreaService(private val loadToursForArea: LoadToursForAreaOutPort): GetToursForAreaUseCase {

    //TODO Fix nullable lists
    private fun shortList(myList: Set<TourDTO>?): List<TourDTO>?{
        //TODO -- apply domain logic for shorting List - Call Tour Domain Service
        return myList?.toList()
    }

    override fun whereAreaIs(area: Int): List<TourDTO>? {
        val tourList = loadToursForArea.whereAreaIs(area)
        // TODO -- call all necessary Output adaptors to form a TourDTO
        // TODO -- call mapper class to convert all data to TourDTO
        return shortList(tourList)
    }

}