package xenagos.application.port.input

import xenagos.application.port.input.model.AdminTopicTagEditRequestDTO
import xenagos.application.port.input.model.AdminTopicTagNewRequestDTO
import xenagos.application.port.input.model.AdminTopicTagResponseDTO
import kotlin.collections.ArrayList
import java.util.UUID

interface AdminTopicTagsUseCase {
    fun getAllTopicTags(): ArrayList<AdminTopicTagResponseDTO>
    fun saveNewTopicTag(requestDTO: AdminTopicTagNewRequestDTO): AdminTopicTagResponseDTO
    fun updateTopicTag(requestDTO: AdminTopicTagEditRequestDTO): AdminTopicTagResponseDTO
    fun deleteTopicTag(id: UUID)
}