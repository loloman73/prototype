package port.output

import port.commun.models.LightXenDTO

interface LoadLightXensForAreaOutPort {
    fun whereAreaIs(area:Int): Set<LightXenDTO>?
}