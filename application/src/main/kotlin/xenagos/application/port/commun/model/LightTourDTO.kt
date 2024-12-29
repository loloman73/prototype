package xenagos.application.port.commun.model

import xenagos.application.commun.Money
import xenagos.application.domain.tour.entity.TourUUID
import xenagos.application.domain.xenagos.XenagosUUID

data class LightTourDTO(val tourId: TourUUID,
                       val xenagosId: XenagosUUID,
                       val lightTourPoints: Set<LightTourPointDTO>,
                       val price: Money)
