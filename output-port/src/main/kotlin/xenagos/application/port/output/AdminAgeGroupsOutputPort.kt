package xenagos.application.port.output

import xenagos.domain.model.AgeGroup

interface AdminAgeGroupsOutputPort {
    fun getAllAgeGroups(): ArrayList<AgeGroup>
}