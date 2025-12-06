package xenagos.adapter.output.persistence.admin

import org.springframework.data.repository.CrudRepository
import xenagos.adapter.output.persistence.model.AmeaTagJpaEntity
import java.util.UUID

interface AdminAmeaTagsRepository : CrudRepository<AmeaTagJpaEntity, UUID>