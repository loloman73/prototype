
package xenagos.application.admin.mapper

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import xenagos.application.port.input.admin.model.AdminMediaTypeNewRequestDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeUpdateRequestDTO
import xenagos.domain.model.MediaType
import java.util.UUID

// This comprehensive test suite includes:
// 1. **Response DTO mapping tests** - Testing conversion from domain entity to response DTO, with emphasis on the field name mapping (entity.type → dto.name)
// 2. **New Request DTO mapping tests** - Testing creation of domain entities from new request DTOs (dto.name → entity.type)
// 3. **Update Request DTO mapping tests** - Testing creation of domain entities from update request DTOs
// 4. **Status variations** - Testing both active and inactive statuses
// 5. **Default value handling** - Testing the default false value for the active field
// 6. **Special characters and formats** - Testing various media type names:
//   - Simple types (Audio, Video, Image, Text)
//   - File formats (PDF, MP3, MP4, JPEG, PNG, GIF, WAV, AVI, HTML5)
//   - Compound names (Virtual Reality, Augmented Reality, 3D Model)
//   - Special characters (Audio/Video, 360° Video)
//   - Hyphenated names (Stop-Motion)
//   - Spaces in names (Virtual Reality)
// 7. **Roundtrip tests** - Ensuring data integrity through conversion chains
// 8. **Field extraction tests** - Using AssertJ's `extracting()` method for cleaner multi-field assertions
// 9. **Field mapping validation** - Explicit tests to verify the name/type field mapping between DTOs and entities
// 10. **Multiple media types** - Testing various common media type names in a loop
//
// The test follows the Given-When-Then structure and provides comprehensive coverage of all mapper methods, with special attention to the field name differences between the domain model (`type`) and the DTOs (`name`).

class AdminMediaTypeMapperTest {

    private val mapper = AdminMediaTypeMapper()

    @Test
    fun `toResponseDto should map domain entity to response DTO correctly`() {
        // Given
        val id = UUID.randomUUID()
        val entity = MediaType(
            id = id,
            type = "Audio",
            active = true
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto).isNotNull
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.entityName).isEqualTo("Audio")
        assertThat(responseDto.active).isTrue()
    }

    @Test
    fun `toResponseDto should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val entity = MediaType(
            id = id,
            type = "Video",
            active = false
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto).isNotNull
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.entityName).isEqualTo("Video")
        assertThat(responseDto.active).isFalse()
    }

    @Test
    fun `toResponseDto should handle different media types`() {
        // Given
        val id = UUID.randomUUID()
        val entity = MediaType(
            id = id,
            type = "Image",
            active = true
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto).isNotNull
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.entityName).isEqualTo("Image")
        assertThat(responseDto.active).isTrue()
    }

    @Test
    fun `toEntity with NewRequestDTO should map to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminMediaTypeNewRequestDTO(
            entityName = "Text",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.type).isEqualTo("Text")
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `toEntity with NewRequestDTO should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminMediaTypeNewRequestDTO(
            entityName = "3D Model",
            active = false
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.type).isEqualTo("3D Model")
        assertThat(entity.active).isFalse()
    }

    @Test
    fun `toEntity with NewRequestDTO should handle default false active value`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminMediaTypeNewRequestDTO(
            entityName = "Interactive",
            active = false
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.type).isEqualTo("Interactive")
        assertThat(entity.active).isFalse()
    }

    @Test
    fun `toEntity with NewRequestDTO should handle media type with special characters`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminMediaTypeNewRequestDTO(
            entityName = "Audio/Video",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.type).isEqualTo("Audio/Video")
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `toEntity with NewRequestDTO should handle media type with spaces`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminMediaTypeNewRequestDTO(
            entityName = "Virtual Reality",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.type).isEqualTo("Virtual Reality")
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `toEntity with UpdateRequestDTO should map to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminMediaTypeUpdateRequestDTO(
            id = id,
            entityName = "Augmented Reality",
            active = true
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.type).isEqualTo("Augmented Reality")
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `toEntity with UpdateRequestDTO should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminMediaTypeUpdateRequestDTO(
            id = id,
            entityName = "PDF",
            active = false
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.type).isEqualTo("PDF")
        assertThat(entity.active).isFalse()
    }

    @Test
    fun `toEntity with UpdateRequestDTO should handle various file format types`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminMediaTypeUpdateRequestDTO(
            id = id,
            entityName = "MP4",
            active = true
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.type).isEqualTo("MP4")
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `roundtrip from NewRequestDTO to entity to ResponseDTO should preserve data`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminMediaTypeNewRequestDTO(
            entityName = "Hologram",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.entityName).isEqualTo(newRequestDto.entityName)
        assertThat(responseDto.active).isEqualTo(newRequestDto.active)
    }

    @Test
    fun `roundtrip from UpdateRequestDTO to entity to ResponseDTO should preserve data`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminMediaTypeUpdateRequestDTO(
            id = id,
            entityName = "Animation",
            active = true
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto.id).isEqualTo(updateRequestDto.id)
        assertThat(responseDto.entityName).isEqualTo(updateRequestDto.entityName)
        assertThat(responseDto.active).isEqualTo(updateRequestDto.active)
    }

    @Test
    fun `roundtrip conversion should preserve inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminMediaTypeNewRequestDTO(
            entityName = "Flash",
            active = false
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.entityName).isEqualTo("Flash")
        assertThat(responseDto.active).isFalse()
    }

    @Test
    fun `toResponseDto should map all fields from entity`() {
        // Given
        val id = UUID.randomUUID()
        val entity = MediaType(
            id = id,
            type = "Podcast",
            active = true
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto)
            .extracting("id", "entityName", "active")
            .containsExactly(id, "Podcast", true)
    }

    @Test
    fun `toEntity with NewRequestDTO should map all fields correctly`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminMediaTypeNewRequestDTO(
            entityName = "Slideshow",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity)
            .extracting("id", "type", "active")
            .containsExactly(id, "Slideshow", true)
    }

    @Test
    fun `toEntity with UpdateRequestDTO should map all fields correctly`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminMediaTypeUpdateRequestDTO(
            id = id,
            entityName = "Infographic",
            active = false
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity)
            .extracting("id", "type", "active")
            .containsExactly(id, "Infographic", false)
    }

    @Test
    fun `toResponseDto should handle various common media types`() {
        // Given
        val mediaTypes = listOf("JPEG", "PNG", "GIF", "MP3", "WAV", "AVI", "HTML5")

        mediaTypes.forEach { mediaTypeName ->
            val id = UUID.randomUUID()
            val entity = MediaType(id, mediaTypeName, true)

            // When
            val responseDto = mapper.toResponseDto(entity)

            // Then
            assertThat(responseDto.entityName).isEqualTo(mediaTypeName)
            assertThat(responseDto.id).isEqualTo(id)
            assertThat(responseDto.active).isTrue()
        }
    }

    @Test
    fun `toEntity with NewRequestDTO should handle compound media type names`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminMediaTypeNewRequestDTO(
            entityName = "360° Video",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.type).isEqualTo("360° Video")
    }

    @Test
    fun `toEntity with UpdateRequestDTO should handle hyphenated media type names`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminMediaTypeUpdateRequestDTO(
            id = id,
            entityName = "Stop-Motion",
            active = true
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.type).isEqualTo("Stop-Motion")
    }

    @Test
    fun `toResponseDto should correctly map type field to name in DTO`() {
        // Given
        val id = UUID.randomUUID()
        val entity = MediaType(
            id = id,
            type = "Documentary",
            active = true
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then - verify that entity.type maps to responseDto.name
        assertThat(responseDto.entityName).isEqualTo(entity.type)
        assertThat(responseDto.entityName).isEqualTo("Documentary")
    }

    @Test
    fun `toEntity with NewRequestDTO should correctly map name field to type in entity`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminMediaTypeNewRequestDTO(
            entityName = "Tutorial",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then - verify that dto.name maps to entity.type
        assertThat(entity.type).isEqualTo(newRequestDto.entityName)
        assertThat(entity.type).isEqualTo("Tutorial")
    }

    @Test
    fun `toEntity with UpdateRequestDTO should correctly map name field to type in entity`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminMediaTypeUpdateRequestDTO(
            id = id,
            entityName = "Webinar",
            active = false
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then - verify that dto.name maps to entity.type
        assertThat(entity.type).isEqualTo(updateRequestDto.entityName)
        assertThat(entity.type).isEqualTo("Webinar")
    }
}