package xenagos.application.port.input

import xenagos.application.port.input.model.AdminTopicTagDTO

interface AdminTopicTagsUseCase {
    fun getAllTopicTags(): List<AdminTopicTagDTO>
}