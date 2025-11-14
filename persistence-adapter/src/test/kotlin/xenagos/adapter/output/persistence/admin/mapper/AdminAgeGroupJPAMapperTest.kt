package xenagos.adapter.output.persistence.admin.mapper

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import xenagos.adapter.output.persistence.model.AgeGroupJpaEntity
import xenagos.domain.model.AgeGroup
import java.util.UUID

// The test suite includes:
// 1. Basic mapping tests for both directions (toJpaEntity and toDomainEntity)
// 2. Active/inactive status tests
// 3. Roundtrip conversion test to ensure data integrity
// 4. Edge case tests (min equals max age, wide age ranges)
// 5. Null handling tests for all required fields (addressing the TODO comment about null checks)

class AdminAgeGroupJPAMapperTest {

    private val mapper = AdminAgeGroupJPAMapper()

    @Test
    fun `toJpaEntity should map domain entity to JPA entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = AgeGroup(
            id = id,
            groupName = "Adults",
            minAge = 18,
            maxAge = 64,
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertEquals(id, jpaEntity.id)
        assertEquals("Adults", jpaEntity.ageGroup)
        assertEquals(18, jpaEntity.minAge)
        assertEquals(64, jpaEntity.maxAge)
        assertTrue(jpaEntity.active!!)
    }

    @Test
    fun `toJpaEntity should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = AgeGroup(
            id = id,
            groupName = "Seniors",
            minAge = 65,
            maxAge = 120,
            active = false
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertEquals(id, jpaEntity.id)
        assertEquals("Seniors", jpaEntity.ageGroup)
        assertEquals(65, jpaEntity.minAge)
        assertEquals(120, jpaEntity.maxAge)
        assertFalse(jpaEntity.active!!)
    }

    @Test
    fun `toDomainEntity should map JPA entity to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val jpaEntity = AgeGroupJpaEntity().apply {
            this.id = id
            this.ageGroup = "Children"
            this.minAge = 0
            this.maxAge = 12
            this.active = true
        }

        // When
        val domainEntity = mapper.toDomainEntity(jpaEntity)

        // Then
        assertEquals(id, domainEntity.id)
        assertEquals("Children", domainEntity.groupName)
        assertEquals(0, domainEntity.minAge)
        assertEquals(12, domainEntity.maxAge)
        assertTrue(domainEntity.active)
    }

    @Test
    fun `toDomainEntity should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val jpaEntity = AgeGroupJpaEntity().apply {
            this.id = id
            this.ageGroup = "Teenagers"
            this.minAge = 13
            this.maxAge = 17
            this.active = false
        }

        // When
        val domainEntity = mapper.toDomainEntity(jpaEntity)

        // Then
        assertEquals(id, domainEntity.id)
        assertEquals("Teenagers", domainEntity.groupName)
        assertEquals(13, domainEntity.minAge)
        assertEquals(17, domainEntity.maxAge)
        assertFalse(domainEntity.active)
    }

    @Test
    fun `roundtrip conversion should preserve data`() {
        // Given
        val id = UUID.randomUUID()
        val originalDomain = AgeGroup(
            id = id,
            groupName = "Young Adults",
            minAge = 18,
            maxAge = 25,
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(originalDomain)
        val resultDomain = mapper.toDomainEntity(jpaEntity)

        // Then
        assertEquals(originalDomain.id, resultDomain.id)
        assertEquals(originalDomain.groupName, resultDomain.groupName)
        assertEquals(originalDomain.minAge, resultDomain.minAge)
        assertEquals(originalDomain.maxAge, resultDomain.maxAge)
        assertEquals(originalDomain.active, resultDomain.active)
    }

    @Test
    fun `toJpaEntity should handle edge case with minAge equals maxAge`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = AgeGroup(
            id = id,
            groupName = "Specific Age",
            minAge = 30,
            maxAge = 30,
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertEquals(id, jpaEntity.id)
        assertEquals("Specific Age", jpaEntity.ageGroup)
        assertEquals(30, jpaEntity.minAge)
        assertEquals(30, jpaEntity.maxAge)
        assertTrue(jpaEntity.active!!)
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when id is null`() {
        // Given
        val jpaEntity = AgeGroupJpaEntity().apply {
            this.id = null
            this.ageGroup = "Test Group"
            this.minAge = 10
            this.maxAge = 20
            this.active = true
        }

        // When & Then
        assertThrows(NullPointerException::class.java) {
            mapper.toDomainEntity(jpaEntity)
        }
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when ageGroup is null`() {
        // Given
        val jpaEntity = AgeGroupJpaEntity().apply {
            this.id = UUID.randomUUID()
            this.ageGroup = null
            this.minAge = 10
            this.maxAge = 20
            this.active = true
        }

        // When & Then
        assertThrows(NullPointerException::class.java) {
            mapper.toDomainEntity(jpaEntity)
        }
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when minAge is null`() {
        // Given
        val jpaEntity = AgeGroupJpaEntity().apply {
            this.id = UUID.randomUUID()
            this.ageGroup = "Test Group"
            this.minAge = null
            this.maxAge = 20
            this.active = true
        }

        // When & Then
        assertThrows(NullPointerException::class.java) {
            mapper.toDomainEntity(jpaEntity)
        }
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when maxAge is null`() {
        // Given
        val jpaEntity = AgeGroupJpaEntity().apply {
            this.id = UUID.randomUUID()
            this.ageGroup = "Test Group"
            this.minAge = 10
            this.maxAge = null
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
        val jpaEntity = AgeGroupJpaEntity().apply {
            this.id = UUID.randomUUID()
            this.ageGroup = "Test Group"
            this.minAge = 10
            this.maxAge = 20
            this.active = null
        }

        // When & Then
        assertThrows(NullPointerException::class.java) {
            mapper.toDomainEntity(jpaEntity)
        }
    }

    @Test
    fun `toJpaEntity should handle wide age range`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = AgeGroup(
            id = id,
            groupName = "All Ages",
            minAge = 0,
            maxAge = 100,
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertEquals(id, jpaEntity.id)
        assertEquals("All Ages", jpaEntity.ageGroup)
        assertEquals(0, jpaEntity.minAge)
        assertEquals(100, jpaEntity.maxAge)
        assertTrue(jpaEntity.active!!)
    }
}