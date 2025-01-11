package xenagos.application.port.output

import xenagos.domain.model.Tour


interface LoadToursForAreaOutPort {
    fun whereAreaIs(area:Int): Set<Tour>?
}