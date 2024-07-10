package xenagos.application.domain.xenagisi.entity

import java.net.URI
import java.util.*

class AudioGuide(val id:UUID,
                 val language: Language,
                 val contentTags:Set<ContentTag>,
                 val ageTags:Set<AgeTag>,
                 val specialAbilityTags:Set<SpecialAbilityTag>,
                 val audioStreamURI:URI ) {
}