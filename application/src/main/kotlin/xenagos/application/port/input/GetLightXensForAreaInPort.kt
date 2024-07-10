package xenagos.application.port.input

import xenagos.application.port.commun.model.LightXenDTO

interface GetLightXensForAreaInPort {

    fun whereAreaIs(area:Int): List<LightXenDTO>?

}