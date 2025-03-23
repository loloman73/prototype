package xenagos.application.service

import org.springframework.stereotype.Service
import xenagos.application.mapper.AdminAccessibilityTagDomainMapper
import xenagos.application.port.input.AdminAccessibilityTagsUseCase
import xenagos.application.port.input.model.AdminAccessibilityTagDTO
import xenagos.application.port.input.model.AdminAccessibilityTagNewDTO
import xenagos.application.port.output.AdminAccessibilityTagsOutputPort
import java.util.*
import kotlin.collections.ArrayList

@Service
class AdminAccessibilityTagsAppService(
    private val persistence: AdminAccessibilityTagsOutputPort,
    private val mapper: AdminAccessibilityTagDomainMapper
) : AdminAccessibilityTagsUseCase {

    override fun getAllAccessibilityTags(): ArrayList<AdminAccessibilityTagDTO> {
        val adminAccessibilityTagsDTO = arrayListOf<AdminAccessibilityTagDTO>()
        persistence.getAllAccessibilityTags()
            .forEach { adminAccessibilityTagsDTO.add(mapper.entityToDTO(it)) }
        return adminAccessibilityTagsDTO
    }

    override fun saveNewAccessibilityTag(adminNewAccessibilityTag: AdminAccessibilityTagNewDTO): AdminAccessibilityTagDTO {
        TODO("Not yet implemented")
    }

    override fun updateAccessibilityTag(accessibilityTagDTO: AdminAccessibilityTagDTO): AdminAccessibilityTagDTO {
        TODO("Not yet implemented")
    }

    override fun deleteAccessibilityTag(accessibilityTagId: UUID) {
        TODO("Not yet implemented")
    }
}