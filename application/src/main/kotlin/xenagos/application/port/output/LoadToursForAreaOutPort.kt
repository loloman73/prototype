package xenagos.application.port.output

import xenagos.application.domain.tour.entity.Tour

interface LoadToursForAreaOutPort {
    fun whereAreaIs(area:Int): Set<Tour>?
}