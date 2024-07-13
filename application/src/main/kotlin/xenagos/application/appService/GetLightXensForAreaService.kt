package xenagos.application.appService

import xenagos.application.port.input.GetLightXensForAreaInPort
import xenagos.application.port.output.LoadLightXensForAreaOutPort
import org.springframework.stereotype.Service
import xenagos.application.port.commun.model.LightXenDTO

@Service
class GetLightXensForAreaService( private val loadLightXensForArea: LoadLightXensForAreaOutPort): GetLightXensForAreaInPort {

    //TODO Fix nullable lists
    private fun shortList(myList: Set<LightXenDTO>?): List<LightXenDTO>?{
        //TODO -- Domain logic for shorting List - Call Xenagisi Domain Service
        return myList?.toList()
    }

    override fun whereAreaIs(area: Int): List<LightXenDTO>? {
        val xenList = loadLightXensForArea.whereAreaIs(area)
        return shortList(xenList)
    }

}