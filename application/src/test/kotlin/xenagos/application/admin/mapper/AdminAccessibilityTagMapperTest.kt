
package xenagos.application.admin.mapper

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import xenagos.application.port.input.admin.model.AdminAccessibilityTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAccessibilityTagUpdateRequestDTO
import xenagos.domain.model.AccessibilityTag
import java.util.UUID

// Basic assertions: assertThat().isEqualTo(), assertThat().isTrue(), assertThat().isFalse()
// Object assertions: assertThat().isNotNull
// Field extraction: assertThat().extracting().containsExactly() for cleaner multi-field assertions

class AdminAccessibilityTagMapperTest {

    private val mapper = AdminAccessibilityTagMapper()

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
        assertThat(responseDto.name).isEqualTo("Wheelchair Accessible")
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
        assertThat(responseDto.name).isEqualTo("Visual Impairment")
        assertThat(responseDto.description).isEqualTo("Suitable for visually impaired visitors")
        assertThat(responseDto.active).isFalse()
    }

    @Test
    fun `toEntity with NewRequestDTO should map to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminAccessibilityTagNewRequestDTO(
            name = "Hearing Impairment",
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
            name = "Mobility Impairment",
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

    @Test
    fun `toEntity with NewRequestDTO should handle default false active value`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminAccessibilityTagNewRequestDTO(
            name = "Test Tag",
            description = "Test Description",
            active = false
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.name).isEqualTo("Test Tag")
        assertThat(entity.description).isEqualTo("Test Description")
        assertThat(entity.active).isFalse()
    }

    @Test
    fun `toEntity with UpdateRequestDTO should map to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminAccessibilityTagUpdateRequestDTO(
            id = id,
            name = "Cognitive Disability",
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
            name = "Sign Language Available",
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

    @Test
    fun `roundtrip from NewRequestDTO to entity to ResponseDTO should preserve data`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminAccessibilityTagNewRequestDTO(
            name = "Audio Guide Available",
            description = "Audio guides provided for accessibility",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.name).isEqualTo(newRequestDto.name)
        assertThat(responseDto.description).isEqualTo(newRequestDto.description)
        assertThat(responseDto.active).isEqualTo(newRequestDto.active)
    }

    @Test
    fun `roundtrip from UpdateRequestDTO to entity to ResponseDTO should preserve data`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminAccessibilityTagUpdateRequestDTO(
            id = id,
            name = "Braille Labels",
            description = "Braille labels available for exhibits",
            active = true
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto.id).isEqualTo(updateRequestDto.id)
        assertThat(responseDto.name).isEqualTo(updateRequestDto.name)
        assertThat(responseDto.description).isEqualTo(updateRequestDto.description)
        assertThat(responseDto.active).isEqualTo(updateRequestDto.active)
    }

    @Test
    fun `toResponseDto should map all fields from entity`() {
        // Given
        val id = UUID.randomUUID()
        val entity = AccessibilityTag(
            id = id,
            name = "Elevator Access",
            description = "Elevator available for all floors",
            active = true
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto)
            .extracting("id", "name", "description", "active")
            .containsExactly(id, "Elevator Access", "Elevator available for all floors", true)
    }

    @Test
    fun `toEntity with NewRequestDTO should map all fields correctly`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminAccessibilityTagNewRequestDTO(
            name = "Tactile Paving",
            description = "Tactile paving for guidance",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity)
            .extracting("id", "name", "description", "active")
            .containsExactly(id, "Tactile Paving", "Tactile paving for guidance", true)
    }

    @Test
    fun `toEntity with UpdateRequestDTO should map all fields correctly`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminAccessibilityTagUpdateRequestDTO(
            id = id,
            name = "Ramp Access",
            description = "Wheelchair ramp available",
            active = false
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity)
            .extracting("id", "name", "description", "active")
            .containsExactly(id, "Ramp Access", "Wheelchair ramp available", false)
    }
}