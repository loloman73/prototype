package xenagos.application.port.commun.models

import xenagos.application.commun.Money
import xenagos.application.domain.xenagisi.entities.XenUUID
import xenagos.application.domain.xenagisi.entities.XenagosUUID


data class LightXenDTO(val xenId: XenUUID,
                       val xenagosId: XenagosUUID,
                       val lightXPoints: Set<LightXPointDTO>,
                       val price: Money
)
