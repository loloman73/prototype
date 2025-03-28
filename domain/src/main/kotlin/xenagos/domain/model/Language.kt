package xenagos.domain.model

import java.util.*

data class Language(val id: UUID,
                    val code: String,
                    val englishName:String,
                    val localName:String)

//en("English","English"),
//de("German","Deutsch"),
//fr("French","Français"),
//ru("Russian","Русский"),
//es("Spanish","Español"),
//zh("Chinese","中文"),
//tr("Turkish","Türkçe")