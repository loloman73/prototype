package xenagos.application.service

import org.springframework.stereotype.Service
import xenagos.application.mapper.AdminAgeGroupsMapper
import xenagos.application.port.input.AdminAgeGroupUseCase
import xenagos.application.port.input.model.AdminAgeGroupDTO
import xenagos.application.port.output.AdminAgeGroupsOutputPort

@Service
class AdminAgeGroupsAppService(
    private val persistence: AdminAgeGroupsOutputPort,
    private val mapper: AdminAgeGroupsMapper
) : AdminAgeGroupUseCase {

    override fun getAllAgeGroups(): ArrayList<AdminAgeGroupDTO> {
        val adminAgeGroupsDTO = arrayListOf<AdminAgeGroupDTO>()
        persistence.getAllAgeGroups().forEach { adminAgeGroupsDTO.add(mapper.entityToDTO(it)) }
        return adminAgeGroupsDTO
    }
}