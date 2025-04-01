package xenagos.application.port.input

import xenagos.application.port.input.model.AdminTopicTagEditRequestDTO
import xenagos.application.port.input.model.AdminTopicTagNewRequestDTO
import xenagos.application.port.input.model.AdminTopicTagResponseDTO
import java.util.UUID

interface AdminTopicTagsUseCase {
    fun getAllTopicTags(): ArrayList<AdminTopicTagResponseDTO>
    fun saveNewTopicTag(adminTopicTagNewRequestDTO: AdminTopicTagNewRequestDTO): AdminTopicTagResponseDTO
    fun updateTopicTag(adminTopicTagEditRequestDTO: AdminTopicTagEditRequestDTO): AdminTopicTagResponseDTO
    fun deleteTopicTag(topicTagId: UUID)
}