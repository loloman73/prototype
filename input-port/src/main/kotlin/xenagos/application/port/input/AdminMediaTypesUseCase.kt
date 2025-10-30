package xenagos.application.port.input

import xenagos.application.port.input.model.AdminMediaTypeResponseDTO

interface AdminMediaTypesUseCase {
    fun getAllMediaTypes(): ArrayList<AdminMediaTypeResponseDTO>
}