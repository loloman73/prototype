package xenagos.application.admin.mapper

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import xenagos.application.port.input.admin.model.AdminAgeGroupNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupResponseDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupUpdateRequestDTO
import xenagos.domain.model.AgeGroup
import java.util.*

class AdminAgeGroupMapperTest {

    private val mapper = AdminAgeGroupMapper()

    @Test
    fun `toResponseDto maps all fields`() {
        val id = UUID.randomUUID()
        val entity = AgeGroup(id, "Teens", 13, 17, true)

        val dto: AdminAgeGroupResponseDTO = mapper.toResponseDto(entity)

        assertThat(dto.id).isEqualTo(id)
        assertThat(dto.groupName).isEqualTo("Teens")
        assertThat(dto.minAge).isEqualTo(13)
        assertThat(dto.maxAge).isEqualTo(17)
        assertThat(dto.active).isTrue()
    }

    @Test
    fun `toEntity from new request uses given id and maps fields`() {
        val id = UUID.randomUUID()
        val newDto = AdminAgeGroupNewRequestDTO(
            groupName = "Adults",
            minAge = 18,
            maxAge = 64,
            active = false
        )

        val entity: AgeGroup = mapper.toEntity(newDto, id)

        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.groupName).isEqualTo("Adults")
        assertThat(entity.minAge).isEqualTo(18)
        assertThat(entity.maxAge).isEqualTo(64)
        assertThat(entity.active).isFalse()
    }

    @Test
    fun `toEntity from update request maps all fields`() {
        val id = UUID.randomUUID()
        val updateDto = AdminAgeGroupUpdateRequestDTO(
            id = id,
            groupName = "Seniors",
            minAge = 65,
            maxAge = 99,
            active = true
        )

        val entity: AgeGroup = mapper.toEntity(updateDto)

        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.groupName).isEqualTo("Seniors")
        assertThat(entity.minAge).isEqualTo(65)
        assertThat(entity.maxAge).isEqualTo(99)
        assertThat(entity.active).isTrue()
    }
}
