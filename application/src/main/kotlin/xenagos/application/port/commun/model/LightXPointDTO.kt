package xenagos.application.port.commun.model

import xenagos.application.domain.tour.commun.Coordinates
import xenagos.application.domain.xenagisi.entity.XPointUUID

data class LightXPointDTO(val xPointId: XPointUUID, val coordinates: Coordinates)


