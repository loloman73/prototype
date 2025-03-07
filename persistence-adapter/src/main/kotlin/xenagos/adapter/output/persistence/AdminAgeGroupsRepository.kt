package xenagos.adapter.output.persistence

import org.springframework.data.repository.CrudRepository
import xenagos.adapter.output.persistence.model.AgeGroupJpaEntity
import java.util.UUID

interface AdminAgeGroupsRepository : CrudRepository<AgeGroupJpaEntity, UUID> {
}