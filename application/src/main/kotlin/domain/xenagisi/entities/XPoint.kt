package domain.xenagisi.entities

import java.util.*

class XPoint (val xPointId: XPointUUID,
              val coordinates: Coordinates,
              val audioGuides: Map<UUID, AudioGuide>)


data class XPointUUID(val value: UUID)