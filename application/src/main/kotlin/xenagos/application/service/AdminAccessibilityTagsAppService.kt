package xenagos.application.service

import org.springframework.stereotype.Service
import xenagos.application.mapper.AdminAccessibilityTagDomainMapper
import xenagos.application.port.input.AdminAccessibilityTagsUseCase
import xenagos.application.port.input.model.AdminAccessibilityTagEditRequestDTO
import xenagos.application.port.input.model.AdminAccessibilityTagResponseDTO
import xenagos.application.port.input.model.AdminAccessibilityTagNewRequestDTO
import xenagos.application.port.output.AdminAccessibilityTagsOutputPort
import java.util.*
import kotlin.collections.ArrayList

@Service
class AdminAccessibilityTagsAppService(
    private val persistence: AdminAccessibilityTagsOutputPort,
    private val mapper: AdminAccessibilityTagDomainMapper
) : AdminAccessibilityTagsUseCase {

    override fun getAllAccessibilityTags(): ArrayList<AdminAccessibilityTagResponseDTO> {
        val adminAccessibilityTagsDTO = arrayListOf<AdminAccessibilityTagResponseDTO>()
        persistence.getAllAccessibilityTags()
            .forEach { adminAccessibilityTagsDTO.add(mapper.entityToDTO(it)) }
        return adminAccessibilityTagsDTO
    }

    override fun saveNewAccessibilityTag(adminAccessibilityTagNewRequest: AdminAccessibilityTagNewRequestDTO): AdminAccessibilityTagResponseDTO {
        TODO("Not yet implemented")
    }

    override fun updateAccessibilityTag(adminAccessibilityTagEditRequestDTO: AdminAccessibilityTagEditRequestDTO): AdminAccessibilityTagResponseDTO {
        TODO("Not yet implemented")
    }

    override fun deleteAccessibilityTag(accessibilityTagId: UUID) {
        TODO("Not yet implemented")
    }
}