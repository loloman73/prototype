package xenagos.adapter.output.persistence

import org.springframework.stereotype.Repository
import xenagos.application.port.output.AdminTopicTagsOutputPort
import xenagos.domain.model.TopicTag
import java.util.*
import kotlin.collections.ArrayList

@Repository
open class AdminTopicTagsPersistence(val adminTopicTagsRepository: AdminTopicTagsRepository): AdminTopicTagsOutputPort
{
    override fun getAllTopicTags(): ArrayList<TopicTag> {

        val mockTopicList = ArrayList<TopicTag>()
        repeat(5) { mockTopicList.add(mockTopic()) }

        // TODO("call repository")
        // TODO ("map JAP Entity to Domain Entity")

        return mockTopicList
    }


    private fun mockTopic(): TopicTag {
        return TopicTag(
            id = UUID.randomUUID(),
            tag = UUID.randomUUID().toString().substring(0, 3),
            description = UUID.randomUUID().toString().substring(0, 15)
        )
    }

}