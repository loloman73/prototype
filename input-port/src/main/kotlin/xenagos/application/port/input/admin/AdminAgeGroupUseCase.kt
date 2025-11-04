package xenagos.application.port.input.admin

import xenagos.application.port.input.admin.model.AdminAgeGroupResponseDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupEditRequestDTO
import java.util.*
import kotlin.collections.ArrayList

interface AdminAgeGroupUseCase {

    fun getAllAgeGroups(): ArrayList<AdminAgeGroupResponseDTO>
    fun saveNewAgeGroup(requestDTO: AdminAgeGroupNewRequestDTO): AdminAgeGroupResponseDTO
    fun updateAgeGroup(requestDTO: AdminAgeGroupEditRequestDTO): AdminAgeGroupResponseDTO
    fun deleteAgeGroup(id: UUID)
}