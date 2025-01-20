package xenagos.application.service

import xenagos.application.port.input.AdminTopicTagsUseCase
import xenagos.application.port.input.model.AdminTopicTagDTO
import xenagos.application.port.output.AdminTopicTagsOutputPort
import java.util.*

class AdminTopicTagsService(private val adminTopicTagsPersistence: AdminTopicTagsOutputPort): AdminTopicTagsUseCase {

    override fun getAllTopicTags(): List<AdminTopicTagDTO> {
        var listEntity = adminTopicTagsPersistence.getAllTopicTags()
        var listDTO = listOf<AdminTopicTagDTO>()
        //TODO map Entity to DTO
        return listDTO
    }
}