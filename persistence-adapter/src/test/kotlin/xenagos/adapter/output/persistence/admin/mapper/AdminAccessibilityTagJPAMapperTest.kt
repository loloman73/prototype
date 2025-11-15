
package xenagos.adapter.output.persistence.admin.mapper

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import xenagos.adapter.output.persistence.model.AccessibilityTagJpaEntity
import xenagos.domain.model.AccessibilityTag
import java.util.UUID

// The test suite includes:
// 1. Basic mapping tests for both directions (toJpaEntity and toDomainEntity)
// 2. Active/inactive status tests
// 3. Roundtrip conversion test to ensure data integrity
// 4. Null handling tests for all required fields


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
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.accessibilityTag).isEqualTo("Wheelchair Accessible")
        assertThat(jpaEntity.description).isEqualTo("Suitable for wheelchair users")
        assertThat(jpaEntity.active).isTrue()
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
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.accessibilityTag).isEqualTo("Visual Impairment")
        assertThat(jpaEntity.description).isEqualTo("Suitable for visually impaired visitors")
        assertThat(jpaEntity.active).isFalse()
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
        assertThat(domainEntity.id).isEqualTo(id)
        assertThat(domainEntity.name).isEqualTo("Hearing Impairment")
        assertThat(domainEntity.description).isEqualTo("Suitable for hearing impaired visitors")
        assertThat(domainEntity.active).isTrue()
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
        assertThat(domainEntity.id).isEqualTo(id)
        assertThat(domainEntity.name).isEqualTo("Mobility Impairment")
        assertThat(domainEntity.description).isEqualTo("Suitable for visitors with mobility issues")
        assertThat(domainEntity.active).isFalse()
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
        assertThat(resultDomain)
            .usingRecursiveComparison()
            .isEqualTo(originalDomain)
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
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
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
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
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
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
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
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
    }
}