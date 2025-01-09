package xenagos.application.port.output

import xenagos.application.entity.Tour

interface LoadToursForAreaOutPort {
    fun whereAreaIs(area:Int): Set<Tour>?
}