package xenagos.application.service

import org.springframework.stereotype.Service
import xenagos.application.mapper.AdminTopicTagMapper
import xenagos.application.port.input.AdminTopicTagsUseCase
import xenagos.application.port.input.model.AdminTopicTagDTO
import xenagos.application.port.output.AdminTopicTagsOutputPort

@Service
class AdminTopicTagsAppService(
    private val persistence: AdminTopicTagsOutputPort,
    private val mapper: AdminTopicTagMapper
) : AdminTopicTagsUseCase {

    override fun getAllTopicTags(): ArrayList<AdminTopicTagDTO> {
        val adminTopicTagsDTO = arrayListOf<AdminTopicTagDTO>()
        persistence.getAllTopicTags().forEach { adminTopicTagsDTO.add(mapper.entityToDto(it)) }
        return adminTopicTagsDTO
    }
}