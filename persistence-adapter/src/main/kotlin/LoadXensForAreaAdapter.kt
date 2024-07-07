import domain.xenagisi.entities.Xen
import port.output.LoadXensForAreaPort

class LoadXensForAreaAdapter: LoadXensForAreaPort {
    override fun whereAreaIs(area: Int): Set<Xen> {
        TODO("Not yet implemented")
    }

}