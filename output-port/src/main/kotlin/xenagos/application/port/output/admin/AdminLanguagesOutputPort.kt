package xenagos.application.port.output.admin

import xenagos.domain.model.Language
import java.util.*
import kotlin.collections.ArrayList

interface AdminLanguagesOutputPort {
    fun getAllLanguages(): ArrayList<Language>
    fun saveNewLanguage(newEntityToSave: Language): Language
    fun updateLanguage(entityToUpdate: Language): Language
    fun deleteLanguage(id: UUID)
}