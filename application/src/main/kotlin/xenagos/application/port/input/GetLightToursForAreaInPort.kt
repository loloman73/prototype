package xenagos.application.port.input

import xenagos.application.port.commun.model.LightTourDTO

interface GetLightToursForAreaInPort {

    fun whereAreaIs(area:Int): List<LightTourDTO>?

}