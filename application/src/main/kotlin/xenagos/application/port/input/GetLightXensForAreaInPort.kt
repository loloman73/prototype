package xenagos.application.port.input

import xenagos.application.port.commun.models.LightXenDTO

interface GetLightXensForAreaInPort {

    fun whereAreaIs(area:Int): List<LightXenDTO>?

}