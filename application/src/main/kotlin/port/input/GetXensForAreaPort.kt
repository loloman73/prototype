package port.input

import domain.xenagisi.entities.Xen

interface GetXensForAreaPort {

    fun whereAreaIs(area:Int): List<Xen>

}