
package xenagos.adapter.output.persistence.admin.mapper

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import xenagos.adapter.output.persistence.model.MediaTypeJpaEntity
import xenagos.domain.model.MediaType
import java.util.UUID

// This unit test includes:
// Positive test cases: Testing both toJpaEntity and toDomainEntity with valid data
// Active/Inactive status: Testing both true and false values for the active field
// Various media types: Testing different types (Audio, Video, Image, Text, 3D, VR, AR, etc.)
// Special characters: Testing media types with spaces, slashes, and special characters
// Roundtrip conversion: Ensuring data integrity when converting domain → JPA → domain
// Null handling tests: Testing the TODO comment for all required fields
// File formats: Testing common file format names (JPEG, MP3, MP4, PDF, etc.)

class AdminMediaTypeJPAMapperTest {

    private val mapper = AdminMediaTypeJPAMapper()

    @Test
    fun `toJpaEntity should map domain entity to JPA entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = MediaType(
            id = id,
            name = "Audio",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.mediaType).isEqualTo("Audio")
        assertThat(jpaEntity.active).isTrue()
    }

    @Test
    fun `toJpaEntity should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = MediaType(
            id = id,
            name = "Video",
            active = false
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.mediaType).isEqualTo("Video")
        assertThat(jpaEntity.active).isFalse()
    }

    @Test
    fun `toJpaEntity should handle different media types`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = MediaType(
            id = id,
            name = "Text",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.mediaType).isEqualTo("Text")
        assertThat(jpaEntity.active).isTrue()
    }

    @Test
    fun `toJpaEntity should handle media type with special characters`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = MediaType(
            id = id,
            name = "Audio/Video",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.mediaType).isEqualTo("Audio/Video")
        assertThat(jpaEntity.active).isTrue()
    }

    @Test
    fun `toJpaEntity should handle media type with spaces`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = MediaType(
            id = id,
            name = "Virtual Reality",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.mediaType).isEqualTo("Virtual Reality")
        assertThat(jpaEntity.active).isTrue()
    }

    @Test
    fun `toJpaEntity should handle file format types`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = MediaType(
            id = id,
            name = "MP3",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.mediaType).isEqualTo("MP3")
        assertThat(jpaEntity.active).isTrue()
    }

    @Test
    fun `toDomainEntity should map JPA entity to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val jpaEntity = MediaTypeJpaEntity().apply {
            this.id = id
            this.mediaType = "Image"
            this.active = true
        }

        // When
        val domainEntity = mapper.toDomainEntity(jpaEntity)

        // Then
        assertThat(domainEntity.id).isEqualTo(id)
        assertThat(domainEntity.name).isEqualTo("Image")
        assertThat(domainEntity.active).isTrue()
    }

    @Test
    fun `toDomainEntity should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val jpaEntity = MediaTypeJpaEntity().apply {
            this.id = id
            this.mediaType = "3D Model"
            this.active = false
        }

        // When
        val domainEntity = mapper.toDomainEntity(jpaEntity)

        // Then
        assertThat(domainEntity.id).isEqualTo(id)
        assertThat(domainEntity.name).isEqualTo("3D Model")
        assertThat(domainEntity.active).isFalse()
    }

    @Test
    fun `roundtrip conversion should preserve data`() {
        // Given
        val id = UUID.randomUUID()
        val originalDomain = MediaType(
            id = id,
            name = "Interactive",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(originalDomain)
        val resultDomain = mapper.toDomainEntity(jpaEntity)

        // Then
        assertThat(resultDomain)
            .usingRecursiveComparison()
            .isEqualTo(originalDomain)
    }

    @Test
    fun `roundtrip conversion should preserve data for inactive media type`() {
        // Given
        val id = UUID.randomUUID()
        val originalDomain = MediaType(
            id = id,
            name = "Augmented Reality",
            active = false
        )

        // When
        val jpaEntity = mapper.toJpaEntity(originalDomain)
        val resultDomain = mapper.toDomainEntity(jpaEntity)

        // Then
        assertThat(resultDomain)
            .usingRecursiveComparison()
            .isEqualTo(originalDomain)
        assertThat(resultDomain.active).isFalse()
    }

    @Test
    fun `toJpaEntity should handle multiple media types with different formats`() {
        // Given
        val mediaTypes = listOf(
            MediaType(UUID.randomUUID(), "PDF", true),
            MediaType(UUID.randomUUID(), "MP3", false),
            MediaType(UUID.randomUUID(), "MP4", true),
            MediaType(UUID.randomUUID(), "JPEG", false),
            MediaType(UUID.randomUUID(), "HTML5", true)
        )

        // When
        val jpaEntities = mediaTypes.map { mapper.toJpaEntity(it) }

        // Then
        assertThat(jpaEntities).hasSize(5)
        assertThat(jpaEntities[0].mediaType).isEqualTo("PDF")
        assertThat(jpaEntities[0].active).isTrue()
        assertThat(jpaEntities[1].mediaType).isEqualTo("MP3")
        assertThat(jpaEntities[1].active).isFalse()
        assertThat(jpaEntities[2].mediaType).isEqualTo("MP4")
        assertThat(jpaEntities[3].mediaType).isEqualTo("JPEG")
        assertThat(jpaEntities[4].mediaType).isEqualTo("HTML5")
    }

    @Test
    fun `toJpaEntity should handle common image formats`() {
        // Given
        val formats = listOf("JPEG", "PNG", "GIF", "SVG", "WebP")

        formats.forEach { format ->
            val id = UUID.randomUUID()
            val domainEntity = MediaType(id, format, true)

            // When
            val jpaEntity = mapper.toJpaEntity(domainEntity)

            // Then
            assertThat(jpaEntity.mediaType).isEqualTo(format)
            assertThat(jpaEntity.active).isTrue()
        }
    }

    @Test
    fun `toJpaEntity should handle common audio formats`() {
        // Given
        val formats = listOf("MP3", "WAV", "AAC", "FLAC", "OGG")

        formats.forEach { format ->
            val id = UUID.randomUUID()
            val domainEntity = MediaType(id, format, true)

            // When
            val jpaEntity = mapper.toJpaEntity(domainEntity)

            // Then
            assertThat(jpaEntity.mediaType).isEqualTo(format)
        }
    }

    @Test
    fun `toJpaEntity should handle common video formats`() {
        // Given
        val formats = listOf("MP4", "AVI", "MOV", "WMV", "WebM")

        formats.forEach { format ->
            val id = UUID.randomUUID()
            val domainEntity = MediaType(id, format, true)

            // When
            val jpaEntity = mapper.toJpaEntity(domainEntity)

            // Then
            assertThat(jpaEntity.mediaType).isEqualTo(format)
        }
    }

    @Test
    fun `toJpaEntity should handle modern media types`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = MediaType(
            id = id,
            name = "360° Video",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.mediaType).isEqualTo("360° Video")
        assertThat(jpaEntity.active).isTrue()
    }

    @Test
    fun `toJpaEntity should handle hyphenated media type names`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = MediaType(
            id = id,
            name = "Stop-Motion",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.mediaType).isEqualTo("Stop-Motion")
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when id is null`() {
        // Given
        val jpaEntity = MediaTypeJpaEntity().apply {
            this.id = null
            this.mediaType = "Audio"
            this.active = true
        }

        // When & Then
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when mediaType is null`() {
        // Given
        val jpaEntity = MediaTypeJpaEntity().apply {
            this.id = UUID.randomUUID()
            this.mediaType = null
            this.active = true
        }

        // When & Then
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when active is null`() {
        // Given
        val jpaEntity = MediaTypeJpaEntity().apply {
            this.id = UUID.randomUUID()
            this.mediaType = "Audio"
            this.active = null
        }

        // When & Then
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
    }

    @Test
    fun `toDomainEntity should handle various media type categories`() {
        // Given
        val categories = listOf(
            "Podcast" to true,
            "Documentary" to false,
            "Tutorial" to true,
            "Animation" to true,
            "Slideshow" to false
        )

        categories.forEach { (mediaTypeName, isActive) ->
            val id = UUID.randomUUID()
            val jpaEntity = MediaTypeJpaEntity().apply {
                this.id = id
                this.mediaType = mediaTypeName
                this.active = isActive
            }

            // When
            val domainEntity = mapper.toDomainEntity(jpaEntity)

            // Then
            assertThat(domainEntity.name).isEqualTo(mediaTypeName)
            assertThat(domainEntity.active).isEqualTo(isActive)
        }
    }

    @Test
    fun `toJpaEntity should handle descriptive media types`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = MediaType(
            id = id,
            name = "Interactive Exhibit",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.mediaType).isEqualTo("Interactive Exhibit")
    }

    @Test
    fun `roundtrip conversion should preserve special characters`() {
        // Given
        val id = UUID.randomUUID()
        val originalDomain = MediaType(
            id = id,
            name = "Audio/Video Mix",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(originalDomain)
        val resultDomain = mapper.toDomainEntity(jpaEntity)

        // Then
        assertThat(resultDomain.name)
            .isEqualTo(originalDomain.name)
            .isEqualTo("Audio/Video Mix")
    }

    @Test
    fun `toJpaEntity should handle emerging media formats`() {
        // Given
        val formats = listOf(
            "Hologram",
            "AR Experience",
            "VR Tour",
            "Mixed Reality",
            "Haptic Feedback"
        )

        formats.forEach { format ->
            val id = UUID.randomUUID()
            val domainEntity = MediaType(id, format, true)

            // When
            val jpaEntity = mapper.toJpaEntity(domainEntity)

            // Then
            assertThat(jpaEntity.mediaType).isEqualTo(format)
        }
    }

    @Test
    fun `toDomainEntity should map all fields correctly from JPA entity`() {
        // Given
        val id = UUID.randomUUID()
        val jpaEntity = MediaTypeJpaEntity().apply {
            this.id = id
            this.mediaType = "Infographic"
            this.active = false
        }

        // When
        val domainEntity = mapper.toDomainEntity(jpaEntity)

        // Then
        assertThat(domainEntity)
            .extracting("id", "type", "active")
            .containsExactly(id, "Infographic", false)
    }

    @Test
    fun `toJpaEntity should handle legacy media formats`() {
        // Given
        val legacyFormats = listOf(
            "Flash" to false,
            "Shockwave" to false,
            "RealPlayer" to false
        )

        legacyFormats.forEach { (format, isActive) ->
            val id = UUID.randomUUID()
            val domainEntity = MediaType(id, format, isActive)

            // When
            val jpaEntity = mapper.toJpaEntity(domainEntity)

            // Then
            assertThat(jpaEntity.mediaType).isEqualTo(format)
            assertThat(jpaEntity.active).isFalse()
        }
    }
}