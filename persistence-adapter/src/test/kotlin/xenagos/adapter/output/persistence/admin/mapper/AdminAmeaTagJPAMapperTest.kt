package xenagos.adapter.output.persistence.admin.mapper

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import xenagos.adapter.output.persistence.model.AmeaTagJpaEntity
import xenagos.domain.model.AmeaTag
import java.util.UUID

class AdminAmeaTagJPAMapperTest {

    private val mapper = AdminAmeaTagJPAMapper()

    @Test
    fun `toJpaEntity should map domain to JPA entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val domain = AmeaTag(id, "TestAmeaTag", "Test Description", true)

        // When
        val jpa = mapper.toJpaEntity(domain)

        // Then
        assertThat(jpa.id).isEqualTo(id)
        assertThat(jpa.ameaTag).isEqualTo("TestAmeaTag")
        assertThat(jpa.description).isEqualTo("Test Description")
        assertThat(jpa.active).isEqualTo(true)
    }

    @Test
    fun `toDomainEntity should map JPA to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val jpa = AmeaTagJpaEntity().apply {
            this.id = id
            this.ameaTag = "TestAmeaTag"
            this.description = "Test Description"
            this.active = true
        }

        // When
        val domain = mapper.toDomainEntity(jpa)

        // Then
        assertThat(domain.id).isEqualTo(id)
        assertThat(domain.name).isEqualTo("TestAmeaTag")
        assertThat(domain.description).isEqualTo("Test Description")
        assertThat(domain.active).isEqualTo(true)
    }

    @Test
    fun `toDomainEntity should throw NullPointerException on null id`() {
        // Given
        val jpa = AmeaTagJpaEntity().apply {
            // id is null
            ameaTag = "TestAmeaTag"
            description = "Test Description"
            active = true
        }

        // When/Then
        assertThatThrownBy { mapper.toDomainEntity(jpa) }
            .isInstanceOf(NullPointerException::class.java)
    }

    @Test
    fun `toDomainEntity should throw NullPointerException on null ameaTag`() {
        // Given
        val jpa = AmeaTagJpaEntity().apply {
            id = UUID.randomUUID()
            // ameaTag is null
            description = "Test Description"
            active = true
        }

        // When/Then
        assertThatThrownBy { mapper.toDomainEntity(jpa) }
            .isInstanceOf(NullPointerException::class.java)
    }

    @Test
    fun `toDomainEntity should throw NullPointerException on null description`() {
        // Given
        val jpa = AmeaTagJpaEntity().apply {
            id = UUID.randomUUID()
            ameaTag = "TestAmeaTag"
            // description is null
            active = true
        }

        // When/Then
        assertThatThrownBy { mapper.toDomainEntity(jpa) }
            .isInstanceOf(NullPointerException::class.java)
    }

    @Test
    fun `toDomainEntity should throw NullPointerException on null active`() {
        // Given
        val jpa = AmeaTagJpaEntity().apply {
            id = UUID.randomUUID()
            ameaTag = "TestAmeaTag"
            description = "Test Description"
            // active is null
        }

        // When/Then
        assertThatThrownBy { mapper.toDomainEntity(jpa) }
            .isInstanceOf(NullPointerException::class.java)
    }
}