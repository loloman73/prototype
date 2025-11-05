package xenagos.application.port.input.admin

import xenagos.application.port.input.admin.model.BaseAdminUpdateRequestDTO
import xenagos.application.port.input.admin.model.BaseAdminNewRequestDTO
import xenagos.application.port.input.admin.model.BaseAdminResponseDTO
import java.util.UUID

interface BaseAdminUseCase<TNew : BaseAdminNewRequestDTO, TUpdate : BaseAdminUpdateRequestDTO, TResponse : BaseAdminResponseDTO> {
    fun getAll(): ArrayList<TResponse>
    fun saveOneNew(requestDTO: TNew): TResponse
    fun updateOne(requestDTO: TUpdate): TResponse
    fun deleteOne(id: UUID)
}