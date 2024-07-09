package port.output

import commun.Money
import domain.xenagisi.entities.XenUUID
import domain.xenagisi.entities.XenagosUUID


data class LightXenDTO(val xenId: XenUUID,
                       val xenagosId: XenagosUUID,
                       val lightXPoints: Set<LightXPointDTO>,
                       val price: Money)
