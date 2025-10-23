package xenagos.adapter.output.persistence

import org.springframework.stereotype.Repository
import xenagos.adapter.output.persistence.mapper.toDomainEntity
import xenagos.adapter.output.persistence.mapper.toJpaEntity
import xenagos.application.port.output.AdminAgeGroupsOutputPort
import xenagos.domain.model.AgeGroup
import java.util.*

@Repository
open class AdminAgeGroupsPersistence(private val repository: AdminAgeGroupsRepository) : AdminAgeGroupsOutputPort {

    override fun getAllAgeGroups() = arrayListOf<AgeGroup>().apply {
        repository.findAll().forEach { add(it.toDomainEntity()) }
    }
    override fun saveNewAgeGroup(newEntityToSave: AgeGroup): AgeGroup =
        repository.save(newEntityToSave.toJpaEntity()).toDomainEntity()

    override fun updateAgeGroup(entityToUpdate: AgeGroup): AgeGroup =
        repository.save(entityToUpdate.toJpaEntity()).toDomainEntity()

    override fun deleteAgeGroup(id: UUID) = repository.deleteById(id)

}