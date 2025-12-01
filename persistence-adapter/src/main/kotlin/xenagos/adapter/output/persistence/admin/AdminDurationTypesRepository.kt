package xenagos.adapter.output.persistence.admin

import org.springframework.data.repository.CrudRepository
import xenagos.adapter.output.persistence.model.DurationTypeJpaEntity
import java.util.UUID

interface AdminDurationTypesRepository: CrudRepository<DurationTypeJpaEntity, UUID>