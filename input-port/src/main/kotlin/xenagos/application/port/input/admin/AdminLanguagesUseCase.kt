package xenagos.application.port.input.admin

import xenagos.application.port.input.admin.model.AdminLanguageEditRequestDTO
import xenagos.application.port.input.admin.model.AdminLanguageNewRequestDTO
import xenagos.application.port.input.admin.model.AdminLanguageResponseDTO
import kotlin.collections.ArrayList
import java.util.*

interface AdminLanguagesUseCase {
    fun getAllLanguages(): ArrayList<AdminLanguageResponseDTO>
    fun saveNewLanguage(requestDTO: AdminLanguageNewRequestDTO): AdminLanguageResponseDTO
    fun updateLanguage(requestDTO: AdminLanguageEditRequestDTO): AdminLanguageResponseDTO
    fun deleteLanguage(id: UUID)
}