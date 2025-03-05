package xenagos.application.port.input

import xenagos.application.port.input.model.AdminAgeGroupDTO

interface AdminAgeGroupUseCase {
    fun getAllAgeGroups(): ArrayList<AdminAgeGroupDTO>
}