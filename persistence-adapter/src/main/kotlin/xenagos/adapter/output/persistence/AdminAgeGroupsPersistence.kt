package xenagos.adapter.output.persistence

import org.springframework.stereotype.Repository
import xenagos.adapter.output.persistence.mapper.toDomainEntity
import xenagos.adapter.output.persistence.mapper.toJpaEntity
import xenagos.application.port.output.AdminAgeGroupsOutputPort
import xenagos.domain.model.AgeGroup
import java.util.*
import kotlin.collections.ArrayList

@Repository
open class AdminAgeGroupsPersistence(private val repository: AdminAgeGroupsRepository): AdminAgeGroupsOutputPort {

    override fun getAllAgeGroups(): ArrayList<AgeGroup> {
        val ageGroupsDomain = arrayListOf<AgeGroup>()
        repository.findAll().forEach { ageGroupsDomain.add(it.toDomainEntity()) }
        return ageGroupsDomain
    }

    override fun saveNewAgeGroup(newEntityToSave: AgeGroup): AgeGroup {
        val returnedJpaEntity = repository.save(newEntityToSave.toJpaEntity())
        return returnedJpaEntity.toDomainEntity()
    }

    override fun updateAgeGroup(entityToUpdate: AgeGroup): AgeGroup {
        val returnedJpaEntity = repository.save(entityToUpdate.toJpaEntity())
        return returnedJpaEntity.toDomainEntity()
    }

    override fun deleteAgeGroup(id: UUID) {
        repository.deleteById(id)
    }


}