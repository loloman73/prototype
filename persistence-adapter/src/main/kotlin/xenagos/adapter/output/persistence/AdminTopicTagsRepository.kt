package xenagos.adapter.output.persistence

import org.springframework.data.repository.CrudRepository
import xenagos.adapter.output.persistence.model.TopicTagJpaEntity

interface AdminTopicTagsRepository: CrudRepository<TopicTagJpaEntity, Long> {

}