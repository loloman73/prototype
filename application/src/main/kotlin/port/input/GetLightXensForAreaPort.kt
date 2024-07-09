package port.input

import port.output.LightXenDTO

interface GetLightXensForAreaPort {

    fun whereAreaIs(area:Int): List<LightXenDTO>?

}