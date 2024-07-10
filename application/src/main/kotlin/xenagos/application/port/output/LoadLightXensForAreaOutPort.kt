package xenagos.application.port.output

import xenagos.application.port.commun.model.LightXenDTO

interface LoadLightXensForAreaOutPort {
    fun whereAreaIs(area:Int): Set<LightXenDTO>?
}