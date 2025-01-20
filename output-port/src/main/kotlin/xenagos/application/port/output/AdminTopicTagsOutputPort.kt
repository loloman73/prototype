package xenagos.application.port.output

import xenagos.domain.model.TopicTag

interface AdminTopicTagsOutputPort {
    fun getAllTopicTags(): List<TopicTag>
}