package xenagos.application.port.input

import xenagos.application.port.input.model.AdminNewTopicTagDTO
import xenagos.application.port.input.model.AdminTopicTagDTO

interface AdminTopicTagsUseCase {
    fun getAllTopicTags(): ArrayList<AdminTopicTagDTO>
    fun saveNewTopicTag(adminNewTopicTagDTO: AdminNewTopicTagDTO): AdminTopicTagDTO
}