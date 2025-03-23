package xenagos.application.port.output

import xenagos.domain.model.TopicTag
import java.util.*
import kotlin.collections.ArrayList

interface AdminTopicTagsOutputPort {
    fun getAllTopicTags(): ArrayList<TopicTag>
    fun saveNewTopicTag(topicTag: TopicTag): TopicTag
    fun updateTopicTag(topicTag: TopicTag): TopicTag
    fun deleteTopicTag(topicTagId: UUID)
}