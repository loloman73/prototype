package xenagos.application.service

import org.springframework.stereotype.Service
import xenagos.application.mapper.AdminAccessibilityTagMapper
import xenagos.application.port.input.AdminAccessibilityTagsUseCase
import xenagos.application.port.input.model.AdminAccessibilityTagDTO
import xenagos.application.port.output.AdminAccessibilityTagsOutputPort

@Service
class AdminAccessibilityTagsAppService(
    private val persistence: AdminAccessibilityTagsOutputPort,
    private val mapper: AdminAccessibilityTagMapper
) : AdminAccessibilityTagsUseCase {

    override fun getAllAccessibilityTags(): ArrayList<AdminAccessibilityTagDTO> {
        val adminAccessibilityTagsDTO = arrayListOf<AdminAccessibilityTagDTO>()
        persistence.getAllAccessibilityTags()
            .forEach { adminAccessibilityTagsDTO.add(mapper.entityToDTO(it)) }
        return adminAccessibilityTagsDTO
    }
}