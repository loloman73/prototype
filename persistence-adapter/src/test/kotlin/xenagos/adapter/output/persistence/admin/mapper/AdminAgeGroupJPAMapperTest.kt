
package xenagos.adapter.output.persistence.admin.mapper

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import xenagos.adapter.output.persistence.model.AgeGroupJpaEntity
import xenagos.domain.model.AgeGroup
import java.util.UUID

// This unit test includes:
// Positive test cases: Testing both toJpaEntity and toDomainEntity with valid data
// Active/Inactive status: Testing both true and false values for the active field
// Edge cases: Testing boundary conditions (minAge equals maxAge, wide age ranges, zero age)
// Roundtrip conversion: Ensuring data integrity when converting domain → JPA → domain
// Null handling tests: Testing the TODO comment for all required fields
// Multiple age groups mapping
// Specific age group scenarios (infants, toddlers, newborns, etc.)

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
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.ageGroup).isEqualTo("Adults")
        assertThat(jpaEntity.minAge).isEqualTo(18)
        assertThat(jpaEntity.maxAge).isEqualTo(64)
        assertThat(jpaEntity.active).isTrue()
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
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.ageGroup).isEqualTo("Seniors")
        assertThat(jpaEntity.minAge).isEqualTo(65)
        assertThat(jpaEntity.maxAge).isEqualTo(120)
        assertThat(jpaEntity.active).isFalse()
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
        assertThat(domainEntity.id).isEqualTo(id)
        assertThat(domainEntity.groupName).isEqualTo("Children")
        assertThat(domainEntity.minAge).isEqualTo(0)
        assertThat(domainEntity.maxAge).isEqualTo(12)
        assertThat(domainEntity.active).isTrue()
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
        assertThat(domainEntity.id).isEqualTo(id)
        assertThat(domainEntity.groupName).isEqualTo("Teenagers")
        assertThat(domainEntity.minAge).isEqualTo(13)
        assertThat(domainEntity.maxAge).isEqualTo(17)
        assertThat(domainEntity.active).isFalse()
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
        assertThat(resultDomain)
            .usingRecursiveComparison()
            .isEqualTo(originalDomain)
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
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.ageGroup).isEqualTo("Specific Age")
        assertThat(jpaEntity.minAge).isEqualTo(30)
        assertThat(jpaEntity.maxAge).isEqualTo(30)
        assertThat(jpaEntity.active).isTrue()
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
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.ageGroup).isEqualTo("All Ages")
        assertThat(jpaEntity.minAge).isEqualTo(0)
        assertThat(jpaEntity.maxAge).isEqualTo(100)
        assertThat(jpaEntity.active).isTrue()
    }

    @Test
    fun `toJpaEntity should handle infants age group`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = AgeGroup(
            id = id,
            groupName = "Infants",
            minAge = 0,
            maxAge = 2,
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.ageGroup).isEqualTo("Infants")
        assertThat(jpaEntity.minAge).isZero()
        assertThat(jpaEntity.maxAge).isEqualTo(2)
        assertThat(jpaEntity.active).isTrue()
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
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
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
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
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
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
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
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
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
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
    }

    @Test
    fun `toJpaEntity should handle multiple age groups with different ranges`() {
        // Given
        val ageGroups = listOf(
            AgeGroup(UUID.randomUUID(), "Toddlers", 3, 5, true),
            AgeGroup(UUID.randomUUID(), "Pre-teens", 10, 12, true),
            AgeGroup(UUID.randomUUID(), "Middle Age", 40, 55, false)
        )

        // When
        val jpaEntities = ageGroups.map { mapper.toJpaEntity(it) }

        // Then
        assertThat(jpaEntities).hasSize(3)
        assertThat(jpaEntities[0].ageGroup).isEqualTo("Toddlers")
        assertThat(jpaEntities[0].minAge).isEqualTo(3)
        assertThat(jpaEntities[0].maxAge).isEqualTo(5)
        assertThat(jpaEntities[1].ageGroup).isEqualTo("Pre-teens")
        assertThat(jpaEntities[2].active).isFalse()
    }

    @Test
    fun `toDomainEntity should handle age group with zero minimum age`() {
        // Given
        val id = UUID.randomUUID()
        val jpaEntity = AgeGroupJpaEntity().apply {
            this.id = id
            this.ageGroup = "Newborns"
            this.minAge = 0
            this.maxAge = 1
            this.active = true
        }

        // When
        val domainEntity = mapper.toDomainEntity(jpaEntity)

        // Then
        assertThat(domainEntity.minAge).isZero()
        assertThat(domainEntity.maxAge).isEqualTo(1)
        assertThat(domainEntity.groupName).isEqualTo("Newborns")
    }
}