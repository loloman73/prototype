package xenagos.adapter.output.persistence.admin

import org.springframework.data.repository.CrudRepository
import xenagos.adapter.output.persistence.model.LanguageJpaEntity
import java.util.UUID

interface AdminLanguagesRepository : CrudRepository<LanguageJpaEntity, UUID> {
}