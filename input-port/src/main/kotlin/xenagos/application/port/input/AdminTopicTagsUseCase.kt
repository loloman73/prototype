package xenagos.application.port.input

import xenagos.application.port.input.model.AdminTopicTagDto

interface AdminTopicTagsUseCase {
    fun getAllTopicTags(): ArrayList<AdminTopicTagDto>
}