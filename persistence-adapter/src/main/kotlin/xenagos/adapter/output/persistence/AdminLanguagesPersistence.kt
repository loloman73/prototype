package xenagos.adapter.output.persistence

import org.springframework.stereotype.Repository
import xenagos.adapter.output.persistence.mapper.toDomainEntity
import xenagos.adapter.output.persistence.mapper.toJpaEntity
import xenagos.application.port.output.AdminLanguagesOutputPort
import xenagos.domain.model.Language
import java.util.*
import kotlin.collections.ArrayList

@Repository
open class AdminLanguagesPersistence(private val repository: AdminLanguagesRepository) : AdminLanguagesOutputPort {

    override fun getAllLanguages(): ArrayList<Language> {
        val domainEntityList = arrayListOf<Language>()
        repository.findAll().forEach { domainEntityList.add(it.toDomainEntity()) }
        return domainEntityList
    }

    override fun saveNewLanguage(newEntityToSave: Language): Language {
        val returnedJpaEntity = repository.save(newEntityToSave.toJpaEntity())
        return returnedJpaEntity.toDomainEntity()
    }

    override fun updateLanguage(entityToUpdate: Language): Language {
        val returnedJpaEntity = repository.save(entityToUpdate.toJpaEntity())
        return returnedJpaEntity.toDomainEntity()
    }

    override fun deleteLanguage(id: UUID) {
        repository.deleteById(id)
    }
}