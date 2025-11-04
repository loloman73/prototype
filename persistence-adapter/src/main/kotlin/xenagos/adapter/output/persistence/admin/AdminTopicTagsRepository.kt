package xenagos.adapter.output.persistence.admin

import org.springframework.data.repository.CrudRepository
import xenagos.adapter.output.persistence.model.TopicTagJpaEntity
import java.util.UUID

interface AdminTopicTagsRepository : CrudRepository<TopicTagJpaEntity, UUID>