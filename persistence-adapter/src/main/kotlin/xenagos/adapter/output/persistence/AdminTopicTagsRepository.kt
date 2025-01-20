package xenagos.adapter.output.persistence

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import xenagos.adapter.output.persistence.model.TopicTag

@Repository
interface AdminTopicTagsRepository: CrudRepository<TopicTag, Long> {

}