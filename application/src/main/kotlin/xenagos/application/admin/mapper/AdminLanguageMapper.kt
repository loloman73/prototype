package xenagos.application.admin.mapper

import org.springframework.stereotype.Component
import xenagos.application.port.input.admin.model.AdminLanguageUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminLanguageNewRequestDTO
import xenagos.application.port.input.admin.model.AdminLanguageResponseDTO
import xenagos.domain.model.Language
import java.util.UUID

@Component
class AdminLanguageMapper : BaseAdminMapper<
        Language,
        AdminLanguageNewRequestDTO,
        AdminLanguageUpdateRequestDTO,
        AdminLanguageResponseDTO> {

    override fun toResponseDto(entity: Language): AdminLanguageResponseDTO =
        AdminLanguageResponseDTO(entity.id, entity.code, entity.englishName, entity.nativeName, entity.active)

    override fun toEntity(dto: AdminLanguageNewRequestDTO, id: UUID): Language =
        Language(id, dto.code, dto.englishName, dto.nativeName, dto.active)

    override fun toEntity(dto: AdminLanguageUpdateRequestDTO): Language =
        Language(dto.id, dto.code, dto.englishName, dto.nativeName, dto.active)
}