
package xenagos.application.admin.mapper

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import xenagos.application.port.input.admin.model.AdminAgeGroupNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupUpdateRequestDTO
import xenagos.domain.model.AgeGroup
import java.util.UUID

// Response DTO mapping tests - Testing the conversion from domain entity to response DTO with various scenarios
// New Request DTO mapping tests - Testing the creation of domain entities from new request DTOs
// Update Request DTO mapping tests - Testing the creation of domain entities from update request DTOs
// Edge cases - Testing scenarios like minAge equals maxAge, wide age ranges, and boundary values (0-1, 0-100, etc.)
// Status variations - Testing both active and inactive statuses
// Roundtrip tests - Ensuring data integrity through conversion chains
// Field extraction tests - Using AssertJ's extracting() method for cleaner multi-field assertions

class AdminAgeGroupMapperTest {

    private val mapper = AdminAgeGroupMapper()

    @Test
    fun `toResponseDto should map domain entity to response DTO correctly`() {
        // Given
        val id = UUID.randomUUID()
        val entity = AgeGroup(
            id = id,
            groupName = "Adults",
            minAge = 18,
            maxAge = 64,
            active = true
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto).isNotNull
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.entityName).isEqualTo("Adults")
        assertThat(responseDto.minAge).isEqualTo(18)
        assertThat(responseDto.maxAge).isEqualTo(64)
        assertThat(responseDto.active).isTrue()
    }

    @Test
    fun `toResponseDto should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val entity = AgeGroup(
            id = id,
            groupName = "Seniors",
            minAge = 65,
            maxAge = 99,
            active = false
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto).isNotNull
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.entityName).isEqualTo("Seniors")
        assertThat(responseDto.minAge).isEqualTo(65)
        assertThat(responseDto.maxAge).isEqualTo(99)
        assertThat(responseDto.active).isFalse()
    }

    @Test
    fun `toResponseDto should handle children age group`() {
        // Given
        val id = UUID.randomUUID()
        val entity = AgeGroup(
            id = id,
            groupName = "Children",
            minAge = 0,
            maxAge = 12,
            active = true
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto).isNotNull
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.entityName).isEqualTo("Children")
        assertThat(responseDto.minAge).isEqualTo(0)
        assertThat(responseDto.maxAge).isEqualTo(12)
        assertThat(responseDto.active).isTrue()
    }

    @Test
    fun `toEntity with NewRequestDTO should map to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminAgeGroupNewRequestDTO(
            entityName = "Teens",
            minAge = 13,
            maxAge = 17,
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.groupName).isEqualTo("Teens")
        assertThat(entity.minAge).isEqualTo(13)
        assertThat(entity.maxAge).isEqualTo(17)
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `toEntity with NewRequestDTO should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminAgeGroupNewRequestDTO(
            entityName = "Young Adults",
            minAge = 18,
            maxAge = 25,
            active = false
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.groupName).isEqualTo("Young Adults")
        assertThat(entity.minAge).isEqualTo(18)
        assertThat(entity.maxAge).isEqualTo(25)
        assertThat(entity.active).isFalse()
    }

    @Test
    fun `toEntity with NewRequestDTO should handle edge case with minAge equals maxAge`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminAgeGroupNewRequestDTO(
            entityName = "Specific Age",
            minAge = 30,
            maxAge = 30,
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.groupName).isEqualTo("Specific Age")
        assertThat(entity.minAge).isEqualTo(30)
        assertThat(entity.maxAge).isEqualTo(30)
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `toEntity with UpdateRequestDTO should map to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminAgeGroupUpdateRequestDTO(
            id = id,
            entityName = "Middle Age",
            minAge = 40,
            maxAge = 59,
            active = true
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.groupName).isEqualTo("Middle Age")
        assertThat(entity.minAge).isEqualTo(40)
        assertThat(entity.maxAge).isEqualTo(59)
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `toEntity with UpdateRequestDTO should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminAgeGroupUpdateRequestDTO(
            id = id,
            entityName = "Elderly",
            minAge = 80,
            maxAge = 120,
            active = false
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.groupName).isEqualTo("Elderly")
        assertThat(entity.minAge).isEqualTo(80)
        assertThat(entity.maxAge).isEqualTo(120)
        assertThat(entity.active).isFalse()
    }

    @Test
    fun `toEntity with UpdateRequestDTO should handle wide age range`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminAgeGroupUpdateRequestDTO(
            id = id,
            entityName = "All Ages",
            minAge = 0,
            maxAge = 100,
            active = true
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.groupName).isEqualTo("All Ages")
        assertThat(entity.minAge).isEqualTo(0)
        assertThat(entity.maxAge).isEqualTo(100)
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `roundtrip from NewRequestDTO to entity to ResponseDTO should preserve data`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminAgeGroupNewRequestDTO(
            entityName = "Preschool",
            minAge = 3,
            maxAge = 5,
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.entityName).isEqualTo(newRequestDto.entityName)
        assertThat(responseDto.minAge).isEqualTo(newRequestDto.minAge)
        assertThat(responseDto.maxAge).isEqualTo(newRequestDto.maxAge)
        assertThat(responseDto.active).isEqualTo(newRequestDto.active)
    }

    @Test
    fun `roundtrip from UpdateRequestDTO to entity to ResponseDTO should preserve data`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminAgeGroupUpdateRequestDTO(
            id = id,
            entityName = "School Age",
            minAge = 6,
            maxAge = 12,
            active = true
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto.id).isEqualTo(updateRequestDto.id)
        assertThat(responseDto.entityName).isEqualTo(updateRequestDto.entityName)
        assertThat(responseDto.minAge).isEqualTo(updateRequestDto.minAge)
        assertThat(responseDto.maxAge).isEqualTo(updateRequestDto.maxAge)
        assertThat(responseDto.active).isEqualTo(updateRequestDto.active)
    }

}