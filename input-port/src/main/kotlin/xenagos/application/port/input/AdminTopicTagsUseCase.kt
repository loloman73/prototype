package xenagos.application.port.input

import xenagos.application.port.input.model.AdminTopicTagNewDTO
import xenagos.application.port.input.model.AdminTopicTagDTO
import java.util.UUID

interface AdminTopicTagsUseCase {
    fun getAllTopicTags(): ArrayList<AdminTopicTagDTO>
    fun saveNewTopicTag(adminNewTopicTagDTO: AdminTopicTagNewDTO): AdminTopicTagDTO
    fun updateTopicTag(adminTopicTagDTO: AdminTopicTagDTO): AdminTopicTagDTO
    fun deleteTopicTag(adminTopicTagDtoId: UUID)
}