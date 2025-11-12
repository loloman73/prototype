package xenagos.application.port.output.admin

import xenagos.domain.model.BaseDomainEntity
import java.util.UUID

interface BaseAdminOutputPort<T: BaseDomainEntity> {
    fun getAll(): ArrayList<T>
    fun saveOneNew(newEntityToSave: T): T
    fun updateOne(entityToUpdate: T): T
    fun deleteOne(id: UUID)
}