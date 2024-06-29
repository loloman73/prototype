package ports.input

import domain.xenagisi.Xen

interface GetXensForArea {

    fun whereAreaIs(area:Int): List<Xen>

}