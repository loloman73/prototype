package xenagos.application.admin.mapper

import xenagos.application.port.input.admin.model.AdminLanguageEditRequestDTO
import xenagos.application.port.input.admin.model.AdminLanguageNewRequestDTO
import xenagos.application.port.input.admin.model.AdminLanguageResponseDTO
import xenagos.domain.model.Language
import java.util.UUID

fun Language.toResponseDto() =
    AdminLanguageResponseDTO(this.id, this.code, this.englishName, this.nativeName, this.active)

fun AdminLanguageNewRequestDTO.toEntity(id: UUID) =
    Language(id, this.code, this.englishName, this.nativeName, this.active)

fun AdminLanguageEditRequestDTO.toEntity() =
    Language(this.id, this.code, this.englishName, this.nativeName, this.active)