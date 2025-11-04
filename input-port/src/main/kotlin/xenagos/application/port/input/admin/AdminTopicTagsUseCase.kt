package xenagos.application.port.input.admin

import xenagos.application.port.input.admin.model.AdminTopicTagEditRequestDTO
import xenagos.application.port.input.admin.model.AdminTopicTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminTopicTagResponseDTO
import kotlin.collections.ArrayList
import java.util.UUID

interface AdminTopicTagsUseCase {
    fun getAllTopicTags(): ArrayList<AdminTopicTagResponseDTO>
    fun saveNewTopicTag(requestDTO: AdminTopicTagNewRequestDTO): AdminTopicTagResponseDTO
    fun updateTopicTag(requestDTO: AdminTopicTagEditRequestDTO): AdminTopicTagResponseDTO
    fun deleteTopicTag(id: UUID)
}