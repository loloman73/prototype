package port.output

import domain.xenagisi.entities.Xen

interface LoadXensForAreaPort {

    fun whereAreaIs(area:Int): Set<Xen>
}