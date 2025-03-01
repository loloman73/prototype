package xenagos.adapter.output.persistence

import org.hibernate.type.descriptor.java.BooleanPrimitiveArrayJavaType
import org.springframework.stereotype.Repository
import xenagos.application.port.output.AdminTopicTagsOutputPort
import xenagos.domain.model.TopicTag
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

@Repository
open class AdminTopicTagsPersistence(val adminTopicTagsRepository: AdminTopicTagsRepository) :
    AdminTopicTagsOutputPort {
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
            name = getRandomString(25),
            description = getRandomString(250),
            active =  Random.nextBoolean ()
        )
    }

    private fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + (" ") + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

}