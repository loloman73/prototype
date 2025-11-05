package xenagos.application.admin.mapper

import xenagos.application.port.input.admin.model.AdminMediaTypeUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeNewRequestDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeResponseDTO
import xenagos.domain.model.MediaType
import java.util.UUID

fun MediaType.toResponseDto() = AdminMediaTypeResponseDTO(this.id, this.type, this.active)

fun AdminMediaTypeNewRequestDTO.toEntity(id: UUID) = MediaType(id, this.name, this.active)

fun AdminMediaTypeUpdateRequestDTO.toEntity() = MediaType(id, this.name, this.active)