import commun.Money
import domain.xenagisi.entities.*
import port.output.LoadLightXensForAreaPort
import java.util.*

class LoadLightXensForAreaHardCopyAdapter: LoadLightXensForAreaPort {
    override fun whereAreaIs(area: Int): Set<Xen>? {
        if (area == 51) {
            val x1p1 = XPoint(UUID.randomUUID(), Coordinates(29.980417,31.134389),)
            val x1p2 = XPoint(UUID.randomUUID(), Coordinates(29.980361,31.132972),)
            val x1p3 = XPoint(UUID.randomUUID(), Coordinates(29.979472,31.132806),)
            val x1p4 = XPoint(UUID.randomUUID(), Coordinates(29.978083,31.132806),)
            val x1 = Xen(xenid = XenUUID(UUID.randomUUID()),
                         xenagosId = XenagosUUID(UUID.randomUUID()),
                         price = Money(12, Currency.getInstance("$")),
                         xPoints = setOf(x1p1, x1p2, x1p3, x1p4)
            )

            return null
        }
        else return null
    }
}