package xenagos.application.admin.mapper

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import xenagos.application.port.input.admin.model.AdminAmeaTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAmeaTagUpdateRequestDTO
import xenagos.domain.model.AmeaTag
import java.util.UUID

class AdminAmeaTagMapperTest {

    private val mapper = AdminAmeaTagMapper()

    // maps Entity to Response DTO
    @Test
    fun `toResponseDto should map domain entity to response DTO correctly`() {
        // Given
        val id = UUID.randomUUID()
        val entity = AmeaTag(
            id = id,
            name = "Blind",
            description = "Suitable for blind visitors",
            active = true
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto).isNotNull
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.entityName).isEqualTo("Blind")
        assertThat(responseDto.description).isEqualTo("Suitable for blind visitors")
        assertThat(responseDto.active).isTrue()
    }
    
    @Test
    fun `toResponseDto should handle inactive status`(){
        // Given
        val id = UUID.randomUUID()
        val entity = AmeaTag(
            id = id,
            name = "Deaf",
            description = "Suitable for deaf visitors",
            active = false
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto).isNotNull
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.entityName).isEqualTo("Deaf")
        assertThat(responseDto.description).isEqualTo("Suitable for deaf visitors")
        assertThat(responseDto.active).isFalse()
    }


    // maps NewRequest DTO to Entity
    @Test
    fun `toEntity with NewRequestDTO should map to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminAmeaTagNewRequestDTO(
            entityName = "Autistic",
            description = "Suitable for autistic visitors",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.name).isEqualTo("Autistic")
        assertThat(entity.description).isEqualTo("Suitable for autistic visitors")
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `toEntity with NewRequestDTO should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminAmeaTagNewRequestDTO(
            entityName = "Mobility",
            description = "Suitable for visitors with mobility issues",
            active = false
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.name).isEqualTo("Mobility")
        assertThat(entity.description).isEqualTo("Suitable for visitors with mobility issues")
        assertThat(entity.active).isFalse()
    }
    
    
    // maps UpdateRequest DTO to Entity
    @Test
    fun `toEntity with UpdateRequestDTO should map to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminAmeaTagUpdateRequestDTO(
            id = id,
            entityName = "Cognitive",
            description = "Suitable for visitors with cognitive disabilities",
            active = true
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.name).isEqualTo("Cognitive")
        assertThat(entity.description).isEqualTo("Suitable for visitors with cognitive disabilities")
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `toEntity with UpdateRequestDTO should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminAmeaTagUpdateRequestDTO(
            id = id,
            entityName = "Sign Language",
            description = "Sign language interpretation available",
            active = false
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.name).isEqualTo("Sign Language")
        assertThat(entity.description).isEqualTo("Sign language interpretation available")
        assertThat(entity.active).isFalse()
    }

}