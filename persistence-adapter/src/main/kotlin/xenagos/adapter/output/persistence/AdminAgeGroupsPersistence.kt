package xenagos.adapter.output.persistence

import org.springframework.stereotype.Repository
import xenagos.application.port.output.AdminAgeGroupsOutputPort
import xenagos.domain.model.AgeGroup
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

@Repository
open class AdminAgeGroupsPersistence(
    private val repository: AdminAgeGroupsRepository
) : AdminAgeGroupsOutputPort {

    override fun getAllAgeGroups(): ArrayList<AgeGroup> {
        val mockAgeGroups = arrayListOf<AgeGroup>()
        repeat(5) { mockAgeGroups.add(mockAgeGroup()) }

        return mockAgeGroups
    }

    private fun mockAgeGroup(): AgeGroup {
        return AgeGroup(
            id = UUID.randomUUID(),
            ageGroup = RandomText.getWords(2),
            minAge = Random.nextBytes(1)[0],
            maxAge = Random.nextBytes(1)[0]
        )
    }
}