package xenagos.adapter.output.persistence.admin

import org.springframework.data.repository.CrudRepository
import xenagos.adapter.output.persistence.model.TopicJpaEntity

interface TopicsRepository:CrudRepository<TopicJpaEntity,Byte>