package appService

import port.input.GetLightXensForAreaPort
import port.output.LoadLightXensForAreaOutPort
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import port.output.LightXenDTO

@Service
@RequiredArgsConstructor
class GetLightXensForAreaService(@Autowired private val loadLightXensForArea: LoadLightXensForAreaOutPort): GetLightXensForAreaPort {

    //TODO Fix nullable lists
    private fun shortList(myList: Set<LightXenDTO>?): List<LightXenDTO>?{
        //TODO -- Domain logic for shorting List - Call Xenagisi Domain Service
        return myList?.toList()
    }

    override fun whereAreaIs(area: Int): List<LightXenDTO>? {
        //TODO -- Load Gens from Persistence
        val xenList = loadLightXensForArea.whereAreaIs(area)

        return shortList(xenList)
    }


}