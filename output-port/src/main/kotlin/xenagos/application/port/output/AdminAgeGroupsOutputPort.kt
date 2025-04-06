package xenagos.application.port.output

import xenagos.domain.model.AgeGroup
import java.util.*
import kotlin.collections.ArrayList

interface AdminAgeGroupsOutputPort {
    fun getAllAgeGroups(): ArrayList<AgeGroup>
    fun saveNewAgeGroup(newEntityToSave: AgeGroup):AgeGroup
    fun updateAgeGroup(entityToUpdate: AgeGroup): AgeGroup
    fun deleteAgeGroup(id: UUID)

}