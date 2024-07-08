package domain.xenagisi.entities

import java.util.*

class XPoint (val id: UUID,
              val coordinates: Coordinates,
              val audioGuides: Map<UUID, AudioGuide>) {


}

