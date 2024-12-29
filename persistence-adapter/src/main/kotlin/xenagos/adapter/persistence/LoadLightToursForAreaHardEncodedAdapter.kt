package xenagos.adapter.persistence

import org.springframework.stereotype.Service
import xenagos.application.commun.Money
import xenagos.application.domain.tour.commun.Coordinates
import xenagos.application.domain.tour.commun.DecimalDegreeLat
import xenagos.application.domain.tour.commun.DecimalDegreeLon
import xenagos.application.domain.tour.entity.*
import xenagos.application.domain.xenagos.XenagosUUID
import xenagos.application.port.commun.model.LightTourPointDTO
import xenagos.application.port.commun.model.LightTourDTO
import xenagos.application.port.output.LoadLightToursForAreaOutPort
import java.util.*

@Service
class LoadLightToursForAreaHardEncodedAdapter: LoadLightToursForAreaOutPort {
    override fun whereAreaIs(area: Int): Set<LightTourDTO>? {
        if (area == 51) {
            val x1p1 = LightTourPointDTO(
                TourPointUUID(UUID.randomUUID()),
                Coordinates(DecimalDegreeLat(29.980417), DecimalDegreeLon(31.134389))
                )
            val x1p2 = LightTourPointDTO(
                TourPointUUID(UUID.randomUUID()),
                Coordinates(DecimalDegreeLat(29.980361), DecimalDegreeLon(31.132972))
                )
            val x1p3 = LightTourPointDTO(
                TourPointUUID(UUID.randomUUID()),
                Coordinates(DecimalDegreeLat(29.979472), DecimalDegreeLon(31.132806))
                )
            val x1p4 = LightTourPointDTO(
                TourPointUUID(UUID.randomUUID()),
                Coordinates(DecimalDegreeLat(29.978083), DecimalDegreeLon(31.132806))
                )
            val x1 = LightTourDTO(tourId = TourUUID(UUID.randomUUID()),
                                  xenagosId = XenagosUUID(UUID.randomUUID()),
                                  price = Money(12, Currency.getInstance("EUR")),
                                  lightTourPoints = setOf(x1p1, x1p2, x1p3, x1p4))
            val x2p1 = LightTourPointDTO(
                TourPointUUID(UUID.randomUUID()), Coordinates(
                    DecimalDegreeLat(29.980417),
                    DecimalDegreeLon(31.134389)
                )
            )
            val x2p2 = LightTourPointDTO(
                TourPointUUID(UUID.randomUUID()), Coordinates(
                    DecimalDegreeLat(29.980361),
                    DecimalDegreeLon(31.132972)
                )
            )
            val x2p3 = LightTourPointDTO(
                TourPointUUID(UUID.randomUUID()), Coordinates(
                    DecimalDegreeLat(29.979472),
                    DecimalDegreeLon(31.132806)
                )
            )
            val x2 = LightTourDTO(tourId = TourUUID(UUID.randomUUID()),
                                  xenagosId = XenagosUUID(UUID.randomUUID()),
                                  price = Money(23, Currency.getInstance("EUR")),
                                  lightTourPoints = setOf(x2p1, x2p2, x2p3))
            val x3p1 = LightTourPointDTO(
                TourPointUUID(UUID.randomUUID()), Coordinates(
                    DecimalDegreeLat(29.980417),
                    DecimalDegreeLon(31.134389)
                )
            )
            val x3p2 = LightTourPointDTO(
                TourPointUUID(UUID.randomUUID()), Coordinates(
                    DecimalDegreeLat(29.980361),
                    DecimalDegreeLon(31.132972)
                )
            )
            val x3p3 = LightTourPointDTO(
                TourPointUUID(UUID.randomUUID()), Coordinates(
                    DecimalDegreeLat(29.979472),
                    DecimalDegreeLon(31.132806)
                )
            )
            val x3p4 = LightTourPointDTO(
                TourPointUUID(UUID.randomUUID()), Coordinates(
                    DecimalDegreeLat(29.978083),
                    DecimalDegreeLon(31.132806)
                )
            )
            val x3p5 = LightTourPointDTO(
                TourPointUUID(UUID.randomUUID()), Coordinates(
                    DecimalDegreeLat(29.978083),
                    DecimalDegreeLon(31.132806)
                )
            )
            val x3 = LightTourDTO(tourId = TourUUID(UUID.randomUUID()),
                                  xenagosId = XenagosUUID(UUID.randomUUID()),
                                  price = Money(49, Currency.getInstance("EUR")),
                                  lightTourPoints = setOf(x3p1, x3p2, x3p3, x3p4, x3p5))

            return setOf(x1, x2, x3)
        }
        else return null
    }
}