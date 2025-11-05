package xenagos.application.admin.mapper

import xenagos.application.port.input.admin.model.AdminAgeGroupUpdateRequestDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupResponseDTO
import xenagos.domain.model.AgeGroup
import java.util.UUID

fun AgeGroup.toResponseDto() = AdminAgeGroupResponseDTO(this.id, this.groupName, this.minAge, this.maxAge, this.active)

fun AdminAgeGroupNewRequestDTO.toEntity(id: UUID) =
    AgeGroup(id, this.groupName, this.minAge, this.maxAge, this.active)

fun AdminAgeGroupUpdateRequestDTO.toEntity() =
    AgeGroup(this.id, this.groupName, this.minAge, this.maxAge, this.active)