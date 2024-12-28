package xenagos.application.domain.xenagos

import java.util.*

class Xenagos(val id:XenagosUUID,
              val name:String,
              val photo:String) {
}

data class XenagosUUID(val value:UUID)