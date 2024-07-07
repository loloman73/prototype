package appService

import domain.xenagisi.entities.Xen
import port.input.GetXensForAreaPort
import port.output.LoadXensForAreaPort
import lombok.RequiredArgsConstructor

@RequiredArgsConstructor
class GetXensForAreaService: GetXensForAreaPort {

    //TODO: lateinit is not an solution. Must inject a Bean
    val loadXensForArea: LoadXensForAreaPort


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