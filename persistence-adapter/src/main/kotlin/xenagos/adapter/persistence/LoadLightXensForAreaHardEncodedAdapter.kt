package xenagos.adapter.persistence

import org.springframework.stereotype.Service
import xenagos.application.commun.Money
import xenagos.application.domain.xenagisi.entity.*
import xenagos.application.port.commun.model.LightXPointDTO
import xenagos.application.port.commun.model.LightXenDTO
import xenagos.application.port.output.LoadLightXensForAreaOutPort
import java.util.*

@Service
class LoadLightXensForAreaHardEncodedAdapter: LoadLightXensForAreaOutPort {
    override fun whereAreaIs(area: Int): Set<LightXenDTO>? {
        if (area == 51) {
            val x1p1 = LightXPointDTO(
                XPointUUID(UUID.randomUUID()),
                Coordinates(DecimalDegreeLat(29.980417),DecimalDegreeLon(31.134389))
                )
            val x1p2 = LightXPointDTO(
                XPointUUID(UUID.randomUUID()),
                Coordinates(DecimalDegreeLat(29.980361),DecimalDegreeLon(31.132972))
                )
            val x1p3 = LightXPointDTO(
                XPointUUID(UUID.randomUUID()),
                Coordinates(DecimalDegreeLat(29.979472),DecimalDegreeLon(31.132806))
                )
            val x1p4 = LightXPointDTO(
                XPointUUID(UUID.randomUUID()),
                Coordinates(DecimalDegreeLat(29.978083),DecimalDegreeLon(31.132806))
                )
            val x1 = LightXenDTO(xenId = XenUUID(UUID.randomUUID()),
                                  xenagosId = XenagosUUID(UUID.randomUUID()),
                                  price = Money(12, Currency.getInstance("EUR")),
                                  lightXPoints = setOf(x1p1, x1p2, x1p3, x1p4))
            val x2p1 = LightXPointDTO(
                XPointUUID(UUID.randomUUID()), Coordinates(
                    DecimalDegreeLat(29.980417),
                    DecimalDegreeLon(31.134389)
                )
            )
            val x2p2 = LightXPointDTO(
                XPointUUID(UUID.randomUUID()), Coordinates(
                    DecimalDegreeLat(29.980361),
                    DecimalDegreeLon(31.132972)
                )
            )
            val x2p3 = LightXPointDTO(
                XPointUUID(UUID.randomUUID()), Coordinates(
                    DecimalDegreeLat(29.979472),
                    DecimalDegreeLon(31.132806)
                )
            )
            val x2 = LightXenDTO(xenId = XenUUID(UUID.randomUUID()),
                                  xenagosId = XenagosUUID(UUID.randomUUID()),
                                  price = Money(23, Currency.getInstance("EUR")),
                                  lightXPoints = setOf(x2p1, x2p2, x2p3))
            val x3p1 = LightXPointDTO(
                XPointUUID(UUID.randomUUID()), Coordinates(
                    DecimalDegreeLat(29.980417),
                    DecimalDegreeLon(31.134389)
                )
            )
            val x3p2 = LightXPointDTO(
                XPointUUID(UUID.randomUUID()), Coordinates(
                    DecimalDegreeLat(29.980361),
                    DecimalDegreeLon(31.132972)
                )
            )
            val x3p3 = LightXPointDTO(
                XPointUUID(UUID.randomUUID()), Coordinates(
                    DecimalDegreeLat(29.979472),
                    DecimalDegreeLon(31.132806)
                )
            )
            val x3p4 = LightXPointDTO(
                XPointUUID(UUID.randomUUID()), Coordinates(
                    DecimalDegreeLat(29.978083),
                    DecimalDegreeLon(31.132806)
                )
            )
            val x3p5 = LightXPointDTO(
                XPointUUID(UUID.randomUUID()), Coordinates(
                    DecimalDegreeLat(29.978083),
                    DecimalDegreeLon(31.132806)
                )
            )
            val x3 = LightXenDTO(xenId = XenUUID(UUID.randomUUID()),
                                  xenagosId = XenagosUUID(UUID.randomUUID()),
                                  price = Money(49, Currency.getInstance("EUR")),
                                  lightXPoints = setOf(x3p1, x3p2, x3p3, x3p4, x3p5))

            return setOf(x1, x2, x3)
        }
        else return null
    }
}