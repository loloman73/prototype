package xenagos.domain.model

import java.util.*

data class Language(
    val id: UUID,
    val code: String,
    val englishName: String,
    val nativeName: String
)

//code: ISO 639-3
//eng, "English","English"
//ell, "Greek", "Ελληνικά"
//deu, "German","Deutsch"
//fra, "French","Français"
//ru, "Russian","Русский"
//es, "Spanish","Español"
//zh, "Chinese","中文"
//tur, "Turkish","Türkçe"