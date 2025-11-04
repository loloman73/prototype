package xenagos.application.port.input.admin

import xenagos.application.port.input.admin.model.AdminMediaTypeEditRequestDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeNewRequestDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeResponseDTO
import java.util.UUID

interface AdminMediaTypesUseCase {
    fun getAllMediaTypes(): ArrayList<AdminMediaTypeResponseDTO>
    fun saveNewMediaType(requestDTO: AdminMediaTypeNewRequestDTO): AdminMediaTypeResponseDTO
    fun updateMediaType(requestDTO: AdminMediaTypeEditRequestDTO): AdminMediaTypeResponseDTO
    fun deleteMediaType(id: UUID)
}