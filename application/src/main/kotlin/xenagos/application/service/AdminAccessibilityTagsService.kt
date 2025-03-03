package xenagos.application.service

import org.springframework.stereotype.Service
import xenagos.application.mapper.AdminAccessibilityTagMapper
import xenagos.application.port.input.AdminAccessibilityTagsUseCase
import xenagos.application.port.input.model.AdminAccessibilityTagDTO
import xenagos.application.port.output.AdminAccessibilityTagsOutputPort

@Service
class AdminAccessibilityTagsService(private val adminAccessibilityTagPersistence: AdminAccessibilityTagsOutputPort) :
    AdminAccessibilityTagsUseCase {

    override fun getAllAccessibilityTags(): ArrayList<AdminAccessibilityTagDTO> {
        val mapper = AdminAccessibilityTagMapper()
        val adminAccessibilityTagsDTO = arrayListOf<AdminAccessibilityTagDTO>()
        adminAccessibilityTagPersistence.getAllAccessibilityTags()
            .forEach { adminAccessibilityTagsDTO.add(mapper.entityToDTO(it)) }
        return adminAccessibilityTagsDTO
    }


}