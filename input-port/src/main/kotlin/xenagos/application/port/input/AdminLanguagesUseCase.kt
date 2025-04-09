package xenagos.application.port.input

import xenagos.application.port.input.model.AdminLanguageEditRequestDTO
import xenagos.application.port.input.model.AdminLanguageNewRequestDTO
import xenagos.application.port.input.model.AdminLanguageResponseDTO
import kotlin.collections.ArrayList
import java.util.*

interface AdminLanguagesUseCase {
    fun getAllLanguages(): ArrayList<AdminLanguageResponseDTO>
    fun saveNewLanguage(requestDTO: AdminLanguageNewRequestDTO): AdminLanguageResponseDTO
    fun updateLanguage(requestDTO: AdminLanguageEditRequestDTO): AdminLanguageResponseDTO
    fun deleteLanguage(id: UUID)
}