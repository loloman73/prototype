package xenagos.adapter.output.persistence.admin.mapper

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import xenagos.adapter.output.persistence.model.MediaTypeJpaEntity
import xenagos.domain.model.MediaType
import java.util.UUID

// The test suite includes:
// 1. Basic mapping tests for both directions
// 2. Active/inactive status tests
// 3. Different media type names (Audio, Video, Text, Image, etc.)
// 4. Special characters and spaces in media type names
// 5. Roundtrip conversion tests for data integrity
// 6. Multiple test cases with different formats (PDF, MP3, MP4, etc.)
// 7. Null handling tests for all required fields (addressing the TODO comment)


class AdminMediaTypeJPAMapperTest {

    private val mapper = AdminMediaTypeJPAMapper()

    @Test
    fun `toJpaEntity should map domain entity to JPA entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = MediaType(
            id = id,
            type = "Audio",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertEquals(id, jpaEntity.id)
        assertEquals("Audio", jpaEntity.mediaType)
        assertTrue(jpaEntity.active!!)
    }

    @Test
    fun `toJpaEntity should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = MediaType(
            id = id,
            type = "Video",
            active = false
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertEquals(id, jpaEntity.id)
        assertEquals("Video", jpaEntity.mediaType)
        assertFalse(jpaEntity.active!!)
    }

    @Test
    fun `toJpaEntity should handle different media types`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = MediaType(
            id = id,
            type = "Text",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertEquals(id, jpaEntity.id)
        assertEquals("Text", jpaEntity.mediaType)
        assertTrue(jpaEntity.active!!)
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
        assertEquals(id, domainEntity.id)
        assertEquals("Image", domainEntity.type)
        assertTrue(domainEntity.active)
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
        assertEquals(id, domainEntity.id)
        assertEquals("3D Model", domainEntity.type)
        assertFalse(domainEntity.active)
    }

    @Test
    fun `roundtrip conversion should preserve data`() {
        // Given
        val id = UUID.randomUUID()
        val originalDomain = MediaType(
            id = id,
            type = "Interactive",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(originalDomain)
        val resultDomain = mapper.toDomainEntity(jpaEntity)

        // Then
        assertEquals(originalDomain.id, resultDomain.id)
        assertEquals(originalDomain.type, resultDomain.type)
        assertEquals(originalDomain.active, resultDomain.active)
    }

    @Test
    fun `toJpaEntity should handle media type with special characters`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = MediaType(
            id = id,
            type = "Audio/Video",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertEquals(id, jpaEntity.id)
        assertEquals("Audio/Video", jpaEntity.mediaType)
        assertTrue(jpaEntity.active!!)
    }

    @Test
    fun `toJpaEntity should handle media type with spaces`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = MediaType(
            id = id,
            type = "Virtual Reality",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertEquals(id, jpaEntity.id)
        assertEquals("Virtual Reality", jpaEntity.mediaType)
        assertTrue(jpaEntity.active!!)
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
        assertThrows(NullPointerException::class.java) {
            mapper.toDomainEntity(jpaEntity)
        }
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
        assertThrows(NullPointerException::class.java) {
            mapper.toDomainEntity(jpaEntity)
        }
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
        assertThrows(NullPointerException::class.java) {
            mapper.toDomainEntity(jpaEntity)
        }
    }

    @Test
    fun `toJpaEntity should handle multiple media types with different formats`() {
        // Given
        val testCases = listOf(
            "PDF" to true,
            "MP3" to false,
            "MP4" to true,
            "JPEG" to false,
            "HTML5" to true
        )

        testCases.forEach { (type, active) ->
            val id = UUID.randomUUID()
            val domainEntity = MediaType(id, type, active)

            // When
            val jpaEntity = mapper.toJpaEntity(domainEntity)

            // Then
            assertEquals(id, jpaEntity.id)
            assertEquals(type, jpaEntity.mediaType)
            assertEquals(active, jpaEntity.active)
        }
    }

    @Test
    fun `roundtrip conversion should preserve data for inactive media type`() {
        // Given
        val id = UUID.randomUUID()
        val originalDomain = MediaType(
            id = id,
            type = "Augmented Reality",
            active = false
        )

        // When
        val jpaEntity = mapper.toJpaEntity(originalDomain)
        val resultDomain = mapper.toDomainEntity(jpaEntity)

        // Then
        assertEquals(originalDomain.id, resultDomain.id)
        assertEquals(originalDomain.type, resultDomain.type)
        assertEquals(originalDomain.active, resultDomain.active)
        assertFalse(resultDomain.active)
    }
}