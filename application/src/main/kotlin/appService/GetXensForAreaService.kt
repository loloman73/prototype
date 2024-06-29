package appService

import commun.Money
import domain.xenagisi.entities.XPoint
import domain.xenagisi.entities.Xen
import domain.xenagisi.entities.XenUUID
import domain.xenagisi.entities.XenagosUUID
import domain.xenagos.Xenagos
import port.input.GetXensForAreaPort
import port.output.LoadXensForAreaPort
import java.math.BigDecimal
import java.util.*

class GetXensForAreaService: GetXensForAreaPort {

    //val loadXensForArea: LoadXensForAreaPort

    private fun shortList(myList: List<Xen>):List<Xen>{
        //TODO -- Domain logic for shorting List - Call Xenagisi Domain Service
        return myList
    }

    override fun whereAreaIs(area: Int): List<Xen> {
        //TODO -- Load Gens from Persistence
        val m1 = Money(BigDecimal.valueOf(12.5), Currency.getInstance("$"))
        val xenUUID = XenUUID(UUID.randomUUID())
        val xenagosUUID = XenagosUUID(UUID.randomUUID())
        val x1=Xen(xenUUID,xenagosUUID, setOf(), m1 )

        return shortList(listOf(x1))
    }


}