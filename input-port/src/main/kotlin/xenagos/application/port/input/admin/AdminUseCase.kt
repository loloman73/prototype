package xenagos.application.port.input.admin

import xenagos.application.port.input.admin.model.AdminUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminNewRequestDTO
import xenagos.application.port.input.admin.model.AdminResponseDTO
import java.util.UUID

interface BaseAdminUseCase<TNew : AdminNewRequestDTO, TUpdate : AdminUpdateRequestDTO, TResponse : AdminResponseDTO> {
    fun getAll(): ArrayList<TResponse>
    fun saveOneNew(requestDTO: TNew): TResponse
    fun updateOne(requestDTO: TUpdate): TResponse
    fun deleteOne(id: UUID)
}