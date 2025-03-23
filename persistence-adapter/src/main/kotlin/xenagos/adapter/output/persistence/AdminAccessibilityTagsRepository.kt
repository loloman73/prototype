package xenagos.adapter.output.persistence

import org.springframework.data.repository.CrudRepository
import xenagos.adapter.output.persistence.model.AccessibilityTagJpaEntity
import java.util.UUID

interface AdminAccessibilityTagsRepository : CrudRepository<AccessibilityTagJpaEntity, UUID>