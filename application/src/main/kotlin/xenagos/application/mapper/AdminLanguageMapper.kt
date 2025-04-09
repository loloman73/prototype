package xenagos.application.mapper

import xenagos.application.port.input.model.AdminLanguageEditRequestDTO
import xenagos.application.port.input.model.AdminLanguageNewRequestDTO
import xenagos.application.port.input.model.AdminLanguageResponseDTO
import xenagos.domain.model.Language
import java.util.UUID

fun Language.toResponseDto(): AdminLanguageResponseDTO {
    return AdminLanguageResponseDTO(this.id, this.code, this.englishName, this.nativeName, this.active)
}

fun AdminLanguageNewRequestDTO.toEntity(id: UUID): Language {
    return Language(id, this.code, this.englishName, this.nativeName, this.active)
}

fun AdminLanguageEditRequestDTO.toEntity(): Language {
    return Language(this.id, this.code, this.englishName, this.nativeName, this.active)
}