package xenagos.domain.model

import java.util.*

data class Language(
    val id: UUID,
    val code: String,
    val englishName: String,
    val nativeName: String,
    val active: Boolean
)

//code: ISO 639-3
//eng, "English", "English"
//ell, "Greek", "Ελληνικά"
//deu, "German", "Deutsch"
//fra, "French", "Français"
//rus, "Russian", "Русский"
//spa, "Spanish", "Español"
//tur, "Turkish", "Türkçe"