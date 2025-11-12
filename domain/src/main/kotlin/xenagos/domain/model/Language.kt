package xenagos.domain.model

import java.util.*

//code: ISO 639-3
data class Language(
    val id: UUID,
    val code: String,
    val englishName: String,
    val nativeName: String,
    val active: Boolean
) : BaseDomainEntity