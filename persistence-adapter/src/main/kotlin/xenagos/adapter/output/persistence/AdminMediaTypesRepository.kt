package xenagos.adapter.output.persistence

import org.springframework.data.repository.CrudRepository
import xenagos.adapter.output.persistence.model.MediaTypeJpaEntity
import java.util.UUID

interface AdminMediaTypesRepository: CrudRepository<MediaTypeJpaEntity, UUID>