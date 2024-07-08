package appService

import domain.xenagisi.entities.Xen
import port.input.GetXensForAreaPort
import port.output.LoadLightXensForAreaPort
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class GetXensForAreaService(@Autowired private val loadXensForArea: LoadLightXensForAreaPort): GetXensForAreaPort {

    private fun shortList(myList: Set<Xen>):List<Xen>{
        //TODO -- Domain logic for shorting List - Call Xenagisi Domain Service
        return myList.toList()
    }

    override fun whereAreaIs(area: Int): List<Xen> {
        //TODO -- Load Gens from Persistence
        val xenList = loadXensForArea.whereAreaIs(area)

        return shortList(xenList)
    }


}