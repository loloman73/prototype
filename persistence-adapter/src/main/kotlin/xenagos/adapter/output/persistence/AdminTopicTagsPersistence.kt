package xenagos.adapter.output.persistence

import org.springframework.stereotype.Service
import xenagos.application.port.output.AdminTopicTagsOutputPort
import xenagos.domain.model.TopicTag

@Service
class AdminTopicTagsPersistence(private val adminTopicTagsRepository: AdminTopicTagsRepository): AdminTopicTagsOutputPort {
    override fun getAllTopicTags(): ArrayList<TopicTag> {

        // TODO("call repository")
        // TODO ("map JAP Entity to Domain Entity")
        return arrayListOf<TopicTag>()
    }
}