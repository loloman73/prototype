package xenagos.application.appService

import org.springframework.stereotype.Service
import xenagos.application.port.commun.model.LightTourDTO
import xenagos.application.port.input.GetLightToursForAreaInPort
import xenagos.application.port.output.LoadLightToursForAreaOutPort

@Service
class GetLightToursForAreaService( private val loadLightToursForArea: LoadLightToursForAreaOutPort): GetLightToursForAreaInPort {

    //TODO Fix nullable lists
    private fun shortList(myList: Set<LightTourDTO>?): List<LightTourDTO>?{
        //TODO -- Domain logic for shorting List - Call Xenagisi Domain Service
        return myList?.toList()
    }

    override fun whereAreaIs(area: Int): List<LightTourDTO>? {
        val tourList = loadLightToursForArea.whereAreaIs(area)
        return shortList(tourList)
    }

}