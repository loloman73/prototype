package xenagos.adapter.output.persistence.admin

import org.springframework.stereotype.Repository
import xenagos.adapter.output.persistence.admin.mapper.toDomainEntity
import xenagos.adapter.output.persistence.admin.mapper.toJpaEntity
import xenagos.application.port.output.admin.AdminLanguagesOutputPort
import xenagos.domain.model.Language
import java.util.*

@Repository
open class AdminLanguagesPersistence(private val repository: AdminLanguagesRepository) : AdminLanguagesOutputPort {

    override fun getAllLanguages() = arrayListOf<Language>().apply {
        repository.findAll().forEach { add(it.toDomainEntity()) }
    }
    override fun saveNewLanguage(newEntityToSave: Language): Language =
        repository.save(newEntityToSave.toJpaEntity()).toDomainEntity()

    override fun updateLanguage(entityToUpdate: Language): Language =
        repository.save(entityToUpdate.toJpaEntity()).toDomainEntity()

    override fun deleteLanguage(id: UUID) = repository.deleteById(id)

}