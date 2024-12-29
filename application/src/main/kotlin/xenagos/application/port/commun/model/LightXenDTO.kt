package xenagos.application.port.commun.model

import xenagos.application.commun.Money
import xenagos.application.domain.tour.entity.TourPointUUID
import xenagos.application.domain.tour.entity.TourUUID
import xenagos.application.domain.xenagos.XenagosUUID

data class LightXenDTO(val xenId: TourUUID,
                       val xenagosId: XenagosUUID,
                       val lightXPoints: Set<LightXPointDTO>,
                       val price: Money)
