package xenagos.adapter.output.persistence.admin

import org.springframework.data.repository.CrudRepository
import xenagos.adapter.output.persistence.admin.mapper.BaseJPAMapper
import xenagos.adapter.output.persistence.model.BaseJPAEntity
import xenagos.application.port.output.admin.BaseAdminOutputPort
import xenagos.domain.model.BaseDomainEntity
import java.util.UUID

abstract class BaseAdminPersistence<TEntity : BaseDomainEntity, TJPAEntity : BaseJPAEntity>
    (
    private val repository: CrudRepository<TJPAEntity, UUID>,
    private val mapper: BaseJPAMapper<TEntity, TJPAEntity>
) : BaseAdminOutputPort<TEntity> {

    override fun getAll() = arrayListOf<TEntity>().apply {
        repository.findAll().forEach { add(mapper.toDomainEntity(it)) }
    }
    override fun saveOneNew(newEntityToSave: TEntity): TEntity =
        mapper.toDomainEntity(repository.save(mapper.toJpaEntity(newEntityToSave)))

    override fun updateOne(entityToUpdate: TEntity): TEntity =
        mapper.toDomainEntity(repository.save(mapper.toJpaEntity(entityToUpdate)))

    override fun deleteOne(id: UUID) = repository.deleteById(id)
}