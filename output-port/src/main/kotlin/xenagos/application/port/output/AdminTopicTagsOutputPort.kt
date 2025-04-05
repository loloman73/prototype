package xenagos.application.port.output

import xenagos.domain.model.TopicTag
import java.util.*
import kotlin.collections.ArrayList

interface AdminTopicTagsOutputPort {
    fun getAllTopicTags(): ArrayList<TopicTag>
    fun saveNewTopicTag(newEntityToSave: TopicTag): TopicTag
    fun updateTopicTag(entityToUpdate: TopicTag): TopicTag
    fun deleteTopicTag(id: UUID)
}