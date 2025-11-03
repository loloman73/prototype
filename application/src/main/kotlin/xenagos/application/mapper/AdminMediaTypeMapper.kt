package xenagos.application.mapper

import xenagos.application.port.input.model.AdminMediaTypeEditRequestDTO
import xenagos.application.port.input.model.AdminMediaTypeNewRequestDTO
import xenagos.application.port.input.model.AdminMediaTypeResponseDTO
import xenagos.domain.model.MediaType
import java.util.UUID

fun MediaType.toResponseDto() = AdminMediaTypeResponseDTO(this.id, this.type, this.active)

fun AdminMediaTypeNewRequestDTO.toEntity(id: UUID) = MediaType(id, this.name, this.active)

fun AdminMediaTypeEditRequestDTO.toEntity() = MediaType(id, this.name, this.active)