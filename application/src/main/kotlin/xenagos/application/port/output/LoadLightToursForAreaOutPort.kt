package xenagos.application.port.output

import xenagos.application.port.commun.model.LightTourDTO

interface LoadLightToursForAreaOutPort {
    fun whereAreaIs(area:Int): Set<LightTourDTO>?
}