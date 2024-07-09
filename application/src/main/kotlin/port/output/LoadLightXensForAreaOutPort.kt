package port.output

interface LoadLightXensForAreaOutPort {
    fun whereAreaIs(area:Int): Set<LightXenDTO>?
}