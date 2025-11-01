package xenagos.application.mapper

import xenagos.application.port.input.model.AdminAgeGroupEditRequestDTO
import xenagos.application.port.input.model.AdminAgeGroupNewRequestDTO
import xenagos.application.port.input.model.AdminAgeGroupResponseDTO
import xenagos.domain.model.AgeGroup
import java.util.UUID

fun AgeGroup.toResponseDto() = AdminAgeGroupResponseDTO(this.id, this.groupName, this.minAge, this.maxAge, this.active)

fun AdminAgeGroupNewRequestDTO.toEntity(id: UUID) =
    AgeGroup(id, this.groupName, this.minAge, this.maxAge, this.active)

fun AdminAgeGroupEditRequestDTO.toEntity() =
    AgeGroup(this.id, this.groupName, this.minAge, this.maxAge, this.active)