package xenagos.application.port.input

import xenagos.application.port.input.model.AdminMediaTypeEditRequestDTO
import xenagos.application.port.input.model.AdminMediaTypeNewRequestDTO
import xenagos.application.port.input.model.AdminMediaTypeResponseDTO
import java.util.UUID

interface AdminMediaTypesUseCase {
    fun getAllMediaTypes(): ArrayList<AdminMediaTypeResponseDTO>
    fun saveNewMediaType(requestDTO: AdminMediaTypeNewRequestDTO): AdminMediaTypeResponseDTO
    fun updateMediaType(requestDTO: AdminMediaTypeEditRequestDTO): AdminMediaTypeResponseDTO
    fun deleteMediaType(id: UUID)
}