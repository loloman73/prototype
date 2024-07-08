package port.output

import domain.xenagisi.entities.Xen

interface LoadLightXensForAreaPort {
    fun whereAreaIs(area:Int): Set<Xen>?
}