package domain.xenagisi

import commun.Money
import java.util.UUID

class Xen(val Xenid:UUID, val xenagosId: UUID, val xPoints:Set<XPoint>, val price: Money) {
}