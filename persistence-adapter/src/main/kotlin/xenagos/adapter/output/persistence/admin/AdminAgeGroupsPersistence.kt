package xenagos.adapter.output.persistence.admin

import org.springframework.stereotype.Repository
import xenagos.adapter.output.persistence.admin.mapper.toDomainEntity
import xenagos.adapter.output.persistence.admin.mapper.toJpaEntity
import xenagos.application.port.output.admin.AdminAgeGroupsOutputPort
import xenagos.domain.model.AgeGroup
import java.util.*

@Repository
open class AdminAgeGroupsPersistence(private val repository: AdminAgeGroupsRepository) : AdminAgeGroupsOutputPort {

    override fun getAll() = arrayListOf<AgeGroup>().apply {
        repository.findAll().forEach { add(it.toDomainEntity()) }
    }
    override fun saveOneNew(newEntityToSave: AgeGroup): AgeGroup =
        repository.save(newEntityToSave.toJpaEntity()).toDomainEntity()

    override fun updateOne(entityToUpdate: AgeGroup): AgeGroup =
        repository.save(entityToUpdate.toJpaEntity()).toDomainEntity()

    override fun deleteOne(id: UUID) = repository.deleteById(id)

}