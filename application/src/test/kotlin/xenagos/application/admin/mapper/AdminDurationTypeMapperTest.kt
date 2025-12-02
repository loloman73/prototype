package xenagos.application.admin.mapper

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import xenagos.application.port.input.admin.model.AdminDurationTypeNewRequestDTO
import xenagos.application.port.input.admin.model.AdminDurationTypeUpdateRequestDTO
import xenagos.domain.model.DurationType
import java.util.UUID

class AdminDurationTypeMapperTest {

    private val mapper = AdminDurationTypeMapper()

    @Test
    fun `toResponseDto should map domain entity to response DTO correctly`() {
        // Given
        val id = UUID.randomUUID()
        val entity = DurationType(
            id = id,
            type = "Half Day",
            active = true
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto).isNotNull
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.entityName).isEqualTo("Half Day")
        assertThat(responseDto.active).isTrue()
    }

    @Test
    fun `toResponseDto should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val entity = DurationType(
            id = id,
            type = "Full Day",
            active = false
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto).isNotNull
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.entityName).isEqualTo("Full Day")
        assertThat(responseDto.active).isFalse()
    }



    @Test
    fun `toEntity with NewRequestDTO should map to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminDurationTypeNewRequestDTO(
            entityName = "Guided Tour",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.type).isEqualTo("Guided Tour")
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `toEntity with NewRequestDTO should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminDurationTypeNewRequestDTO(
            entityName = "Audio Guide",
            active = false
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.type).isEqualTo("Audio Guide")
        assertThat(entity.active).isFalse()
    }



    @Test
    fun `toEntity with UpdateRequestDTO should map to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminDurationTypeUpdateRequestDTO(
            id = id,
            entityName = "Extended Tour",
            active = false
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.type).isEqualTo("Extended Tour")
        assertThat(entity.active).isFalse()
    }

    @Test
    fun `toEntity with UpdateRequestDTO should default active to false when not provided`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminDurationTypeUpdateRequestDTO(
            id = id,
            entityName = "Extended Tour",
        ) // active defaults to false

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.type).isEqualTo("Extended Tour")
        assertThat(entity.active).isFalse()
    }

}
