package port.input

import port.commun.models.LightXenDTO

interface GetLightXensForAreaInPort {

    fun whereAreaIs(area:Int): List<LightXenDTO>?

}