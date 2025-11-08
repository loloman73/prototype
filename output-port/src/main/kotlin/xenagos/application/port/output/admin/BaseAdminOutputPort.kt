package xenagos.application.port.output.admin

import java.util.UUID

interface BaseAdminOutputPort<T> {
    fun getAll(): ArrayList<T>
    fun saveOneNew(newEntityToSave: T): T
    fun updateOne(entityToUpdate: T): T
    fun deleteOne(id: UUID)
}