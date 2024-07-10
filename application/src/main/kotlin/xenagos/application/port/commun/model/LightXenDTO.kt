package xenagos.application.port.commun.model

import xenagos.application.commun.Money
import xenagos.application.domain.xenagisi.entity.XenUUID
import xenagos.application.domain.xenagisi.entity.XenagosUUID


data class LightXenDTO(val xenId: XenUUID,
                       val xenagosId: XenagosUUID,
                       val lightXPoints: Set<LightXPointDTO>,
                       val price: Money
)
