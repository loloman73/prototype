package xenagos.adapter.output.persistence.admin

import org.springframework.data.repository.NoRepositoryBean
import xenagos.adapter.output.persistence.admin.mapper.BaseJPAMapper
import xenagos.adapter.output.persistence.model.BaseJPAEntity
import xenagos.application.port.output.admin.BaseAdminOutputPort
import xenagos.domain.model.BaseDomainEntity

@NoRepositoryBean
abstract class BaseAdminPersistence<TEntity : BaseDomainEntity, TJPAEntity: BaseJPAEntity>
    (private val repository: BaseAdminRepository,
    private val mapper: BaseJPAMapper<TEntity, TJPAEntity>) :
    BaseAdminOutputPort<TEntity> {

    override fun getAll() = arrayListOf<TEntity>().apply {
        repository.findAll().forEach { add(mapper.toDomainEntity(it as TJPAEntity)) }
    }
}
