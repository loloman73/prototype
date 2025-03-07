package xenagos.adapter.output.persistence

import org.springframework.stereotype.Repository
import xenagos.application.port.output.AdminTopicTagsOutputPort
import xenagos.domain.model.TopicTag
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

@Repository
open class AdminTopicTagsPersistence(
    private val repository: AdminTopicTagsRepository
) :  AdminTopicTagsOutputPort {

    override fun getAllTopicTags(): ArrayList<TopicTag> {

        val mockTopicList = arrayListOf<TopicTag>()
        repeat(5) { mockTopicList.add(mockTopic()) }

        // TODO("call repository")
        // TODO ("map JAP Entity to Domain Entity")

        return mockTopicList
    }


    private fun mockTopic(): TopicTag {

        return TopicTag(
            id = UUID.randomUUID(),
            name = RandomText.getWords(3),
            description = RandomText.getWords(15),
            active =  Random.nextBoolean ()
        )
    }

}