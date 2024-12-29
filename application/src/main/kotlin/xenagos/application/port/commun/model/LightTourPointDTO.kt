package xenagos.application.port.commun.model

import xenagos.application.domain.tour.commun.Coordinates
import xenagos.application.domain.tour.entity.TourPointUUID

data class LightTourPointDTO(val tourPointId: TourPointUUID, val coordinates: Coordinates)


