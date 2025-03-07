package xenagos.application.mapper

import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import xenagos.application.port.input.model.AdminAgeGroupDTO
import xenagos.domain.model.AgeGroup

@Component
class AdminAgeGroupsMapper {

    fun entityToDTO(entity: AgeGroup): AdminAgeGroupDTO {
        return AdminAgeGroupDTO(entity.id, entity.ageGroup, entity.minAge, entity.maxAge)
    }

    fun dtoToEntity(dto: AdminAgeGroupDTO): AgeGroup {
        return AgeGroup(dto.id, dto.ageGroup, dto.minAge, dto.maxAge)
    }

}