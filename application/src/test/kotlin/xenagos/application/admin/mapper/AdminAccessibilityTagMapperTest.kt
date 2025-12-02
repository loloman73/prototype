
package xenagos.application.admin.mapper

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import xenagos.application.port.input.admin.model.AdminAccessibilityTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAccessibilityTagUpdateRequestDTO
import xenagos.domain.model.AccessibilityTag
import java.util.UUID

class AdminAccessibilityTagMapperTest {

    private val mapper = AdminAccessibilityTagMapper()

    // maps Entity to Response DTO
    @Test
    fun `toResponseDto should map domain entity to response DTO correctly`() {
        // Given
        val id = UUID.randomUUID()
        val entity = AccessibilityTag(
            id = id,
            name = "Wheelchair Accessible",
            description = "Suitable for wheelchair users",
            active = true
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto).isNotNull
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.entityName).isEqualTo("Wheelchair Accessible")
        assertThat(responseDto.description).isEqualTo("Suitable for wheelchair users")
        assertThat(responseDto.active).isTrue()
    }

    @Test
    fun `toResponseDto should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val entity = AccessibilityTag(
            id = id,
            name = "Visual Impairment",
            description = "Suitable for visually impaired visitors",
            active = false
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto).isNotNull
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.entityName).isEqualTo("Visual Impairment")
        assertThat(responseDto.description).isEqualTo("Suitable for visually impaired visitors")
        assertThat(responseDto.active).isFalse()
    }


    // maps NewRequest DTO to Entity
    @Test
    fun `toEntity with NewRequestDTO should map to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminAccessibilityTagNewRequestDTO(
            entityName = "Hearing Impairment",
            description = "Suitable for hearing impaired visitors",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.name).isEqualTo("Hearing Impairment")
        assertThat(entity.description).isEqualTo("Suitable for hearing impaired visitors")
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `toEntity with NewRequestDTO should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminAccessibilityTagNewRequestDTO(
            entityName = "Mobility Impairment",
            description = "Suitable for visitors with mobility issues",
            active = false
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.name).isEqualTo("Mobility Impairment")
        assertThat(entity.description).isEqualTo("Suitable for visitors with mobility issues")
        assertThat(entity.active).isFalse()
    }


    // mpas UpdateRequest DTO to Entity
    @Test
    fun `toEntity with UpdateRequestDTO should map to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminAccessibilityTagUpdateRequestDTO(
            id = id,
            entityName = "Cognitive Disability",
            description = "Suitable for visitors with cognitive disabilities",
            active = true
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.name).isEqualTo("Cognitive Disability")
        assertThat(entity.description).isEqualTo("Suitable for visitors with cognitive disabilities")
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `toEntity with UpdateRequestDTO should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminAccessibilityTagUpdateRequestDTO(
            id = id,
            entityName = "Sign Language Available",
            description = "Sign language interpretation available",
            active = false
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.name).isEqualTo("Sign Language Available")
        assertThat(entity.description).isEqualTo("Sign language interpretation available")
        assertThat(entity.active).isFalse()
    }

}