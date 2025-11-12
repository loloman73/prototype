package xenagos.adapter.output.persistence.admin

import org.springframework.stereotype.Repository
import xenagos.adapter.output.persistence.admin.mapper.toDomainEntity
import xenagos.adapter.output.persistence.admin.mapper.toJpaEntity
import xenagos.application.port.output.admin.AdminLanguagesOutputPort
import xenagos.domain.model.Language
import java.util.*

@Repository
open class AdminLanguagesPersistence(private val repository: AdminLanguagesRepository) :
    AdminLanguagesOutputPort {

    override fun getAll() = arrayListOf<Language>().apply {
        repository.findAll().forEach { add(it.toDomainEntity()) }
    }
    override fun saveOneNew(newEntityToSave: Language): Language =
        repository.save(newEntityToSave.toJpaEntity()).toDomainEntity()

    override fun updateOne(entityToUpdate: Language): Language =
        repository.save(entityToUpdate.toJpaEntity()).toDomainEntity()

    override fun deleteOne(id: UUID) = repository.deleteById(id)
}