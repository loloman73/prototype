package xenagos.adapter.output.persistence

import org.springframework.data.repository.CrudRepository
import xenagos.adapter.output.persistence.model.TopicTagJpaEntity
import java.util.UUID

interface AdminTopicTagsRepository: CrudRepository<TopicTagJpaEntity, UUID> {

}