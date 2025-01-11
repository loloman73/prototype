package xenagos.application.mapper

import org.springframework.stereotype.Component
import xenagos.application.port.input.model.TourDTO
import xenagos.domain.model.Tour
import xenagos.domain.model.TourUUID
import xenagos.domain.model.Xenagos

@Component
class Mapper {
    fun toDTO(fromEntityTour: Tour, fromEntityXenagos: Xenagos): TourDTO {

        return TourDTO(fromEntityTour.tourId.value, fromEntityXenagos.id.value, fromEntityTour.price, 10)
    }
}