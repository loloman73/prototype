package xenagos.adapter.output.persistence

import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import xenagos.application.port.output.AdminTopicTagsOutputPort
import xenagos.domain.model.TopicTag

@Repository
open class AdminTopicTagsPersistence(val adminTopicTagsRepository: AdminTopicTagsRepository): AdminTopicTagsOutputPort {
    override fun getAllTopicTags(): ArrayList<TopicTag> {

        // TODO("call repository")
        // TODO ("map JAP Entity to Domain Entity")
        return arrayListOf<TopicTag>()
    }
}