package xenagos.adapter.output.persistence.admin.mapper

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import xenagos.adapter.output.persistence.model.DurationTypeJpaEntity
import xenagos.domain.model.DurationType
import java.util.UUID

// The test suite mirrors AdminAccessibilityTagJPAMapperTest and includes:
// 1. Basic mapping tests for both directions (toJpaEntity and toDomainEntity)
// 2. Active/inactive status tests
// 3. Roundtrip conversion test to ensure data integrity
// 4. Null handling tests for all required fields

class AdminDurationTypeJPAMapperTest {

    private val mapper = AdminDurationTypeJPAMapper()

    @Test
    fun `toJpaEntity should map domain entity to JPA entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = DurationType(
            id = id,
            name = "Short",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.durationType).isEqualTo("Short")
        assertThat(jpaEntity.active).isTrue()
    }

    @Test
    fun `toJpaEntity should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = DurationType(
            id = id,
            name = "Long",
            active = false
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.durationType).isEqualTo("Long")
        assertThat(jpaEntity.active).isFalse()
    }

    @Test
    fun `toDomainEntity should map JPA entity to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val jpaEntity = DurationTypeJpaEntity().apply {
            this.id = id
            this.durationType = "Medium"
            this.active = true
        }

        // When
        val domainEntity = mapper.toDomainEntity(jpaEntity)

        // Then
        assertThat(domainEntity.id).isEqualTo(id)
        assertThat(domainEntity.name).isEqualTo("Medium")
        assertThat(domainEntity.active).isTrue()
    }

    @Test
    fun `toDomainEntity should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val jpaEntity = DurationTypeJpaEntity().apply {
            this.id = id
            this.durationType = "Very Long"
            this.active = false
        }

        // When
        val domainEntity = mapper.toDomainEntity(jpaEntity)

        // Then
        assertThat(domainEntity.id).isEqualTo(id)
        assertThat(domainEntity.name).isEqualTo("Very Long")
        assertThat(domainEntity.active).isFalse()
    }

    @Test
    fun `roundtrip conversion should preserve data`() {
        // Given
        val id = UUID.randomUUID()
        val originalDomain = DurationType(
            id = id,
            name = "Custom",
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
        val jpaEntity = DurationTypeJpaEntity().apply {
            this.id = null
            this.durationType = "Test"
            this.active = true
        }

        // When & Then
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when durationType is null`() {
        // Given
        val jpaEntity = DurationTypeJpaEntity().apply {
            this.id = UUID.randomUUID()
            this.durationType = null
            this.active = true
        }

        // When & Then
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when active is null`() {
        // Given
        val jpaEntity = DurationTypeJpaEntity().apply {
            this.id = UUID.randomUUID()
            this.durationType = "Test"
            this.active = null
        }

        // When & Then
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
    }
}
