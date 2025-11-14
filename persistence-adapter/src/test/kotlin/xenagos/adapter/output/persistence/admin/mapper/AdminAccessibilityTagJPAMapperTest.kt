package xenagos.adapter.output.persistence.admin.mapper

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import xenagos.adapter.output.persistence.model.AccessibilityTagJpaEntity
import xenagos.domain.model.AccessibilityTag
import java.util.UUID

// This unit test includes:
// Positive test cases: Testing both toJpaEntity and toDomainEntity with valid data
// Active/Inactive status: Testing both true and false values for the active field
// Roundtrip conversion: Ensuring data integrity when converting domain → JPA → domain
// Null handling tests: Testing the TODO comment


class AdminAccessibilityTagJPAMapperTest {

    private val mapper = AdminAccessibilityTagJPAMapper()

    @Test
    fun `toJpaEntity should map domain entity to JPA entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = AccessibilityTag(
            id = id,
            name = "Wheelchair Accessible",
            description = "Suitable for wheelchair users",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertEquals(id, jpaEntity.id)
        assertEquals("Wheelchair Accessible", jpaEntity.accessibilityTag)
        assertEquals("Suitable for wheelchair users", jpaEntity.description)
        assertTrue(jpaEntity.active!!)
    }

    @Test
    fun `toJpaEntity should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = AccessibilityTag(
            id = id,
            name = "Visual Impairment",
            description = "Suitable for visually impaired visitors",
            active = false
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertEquals(id, jpaEntity.id)
        assertEquals("Visual Impairment", jpaEntity.accessibilityTag)
        assertEquals("Suitable for visually impaired visitors", jpaEntity.description)
        assertFalse(jpaEntity.active!!)
    }

    @Test
    fun `toDomainEntity should map JPA entity to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val jpaEntity = AccessibilityTagJpaEntity().apply {
            this.id = id
            this.accessibilityTag = "Hearing Impairment"
            this.description = "Suitable for hearing impaired visitors"
            this.active = true
        }

        // When
        val domainEntity = mapper.toDomainEntity(jpaEntity)

        // Then
        assertEquals(id, domainEntity.id)
        assertEquals("Hearing Impairment", domainEntity.name)
        assertEquals("Suitable for hearing impaired visitors", domainEntity.description)
        assertTrue(domainEntity.active)
    }

    @Test
    fun `toDomainEntity should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val jpaEntity = AccessibilityTagJpaEntity().apply {
            this.id = id
            this.accessibilityTag = "Mobility Impairment"
            this.description = "Suitable for visitors with mobility issues"
            this.active = false
        }

        // When
        val domainEntity = mapper.toDomainEntity(jpaEntity)

        // Then
        assertEquals(id, domainEntity.id)
        assertEquals("Mobility Impairment", domainEntity.name)
        assertEquals("Suitable for visitors with mobility issues", domainEntity.description)
        assertFalse(domainEntity.active)
    }

    @Test
    fun `roundtrip conversion should preserve data`() {
        // Given
        val id = UUID.randomUUID()
        val originalDomain = AccessibilityTag(
            id = id,
            name = "Cognitive Disability",
            description = "Suitable for visitors with cognitive disabilities",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(originalDomain)
        val resultDomain = mapper.toDomainEntity(jpaEntity)

        // Then
        assertEquals(originalDomain.id, resultDomain.id)
        assertEquals(originalDomain.name, resultDomain.name)
        assertEquals(originalDomain.description, resultDomain.description)
        assertEquals(originalDomain.active, resultDomain.active)
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when id is null`() {
        // Given
        val jpaEntity = AccessibilityTagJpaEntity().apply {
            this.id = null
            this.accessibilityTag = "Test Tag"
            this.description = "Test Description"
            this.active = true
        }

        // When & Then
        assertThrows(NullPointerException::class.java) {
            mapper.toDomainEntity(jpaEntity)
        }
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when accessibilityTag is null`() {
        // Given
        val jpaEntity = AccessibilityTagJpaEntity().apply {
            this.id = UUID.randomUUID()
            this.accessibilityTag = null
            this.description = "Test Description"
            this.active = true
        }

        // When & Then
        assertThrows(NullPointerException::class.java) {
            mapper.toDomainEntity(jpaEntity)
        }
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when description is null`() {
        // Given
        val jpaEntity = AccessibilityTagJpaEntity().apply {
            this.id = UUID.randomUUID()
            this.accessibilityTag = "Test Tag"
            this.description = null
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
        val jpaEntity = AccessibilityTagJpaEntity().apply {
            this.id = UUID.randomUUID()
            this.accessibilityTag = "Test Tag"
            this.description = "Test Description"
            this.active = null
        }

        // When & Then
        assertThrows(NullPointerException::class.java) {
            mapper.toDomainEntity(jpaEntity)
        }
    }
}