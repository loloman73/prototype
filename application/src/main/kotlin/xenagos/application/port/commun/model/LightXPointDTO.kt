package xenagos.application.port.commun.model

import xenagos.application.domain.tour.commun.Coordinates
import xenagos.application.domain.tour.entity.TourPointUUID

data class LightXPointDTO(val xPointId: TourPointUUID, val coordinates: Coordinates)


