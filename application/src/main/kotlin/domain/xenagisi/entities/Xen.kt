package domain.xenagisi.entities

import commun.Money

class Xen(val xenid: XenUUID, val xenagosId: XenagosUUID, val xPoints:Set<XPoint>, val price: Money) {
}