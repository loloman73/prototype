package domain.xenagisi.entities

import commun.Money
import java.util.*

class Xen(val xenId: XenUUID,
          val xenagosId: XenagosUUID,
          val xPoints: Set<XPoint>,
          val price: Money) {

    fun midPoint(): XPoint{

        return TODO("Provide the return value")
    }

}


data class XenUUID(val value: UUID)

