package xenagos.adapter.output.persistence.admin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import xenagos.adapter.output.persistence.BasePersistenceIT
import xenagos.adapter.output.persistence.admin.mapper.AdminAmeaTagJPAMapper
import xenagos.domain.model.AmeaTag
import java.util.UUID

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(AdminAmeaTagJPAMapper::class, AdminAmeaTagsPersistence::class)
open class AdminAmeaTagsPersistenceIT : BasePersistenceIT() {

    @Autowired
    lateinit var persistence: AdminAmeaTagsPersistence

    /** Happy path tests **/
    // CREATE
    @Test
    fun `saveOneNew persists a new entry and returns it`() {
        // Arrange
        val newTag = AmeaTag(
            UUID.randomUUID(),
            "Blind",
            "Suitable for blind visitors",
            true
        )

        // Act
        val saved = persistence.saveOneNew(newTag)

        // Assert
        assertThat(saved.id).isEqualTo(newTag.id)
        assertThat(saved.name).isEqualTo("Blind")
        assertThat(saved.description).isEqualTo("Suitable for blind visitors")
        assertThat(saved.active).isTrue()
    }

    // READ
    @Test
    fun `getAll returns persisted entries`() {
        // Arrange
        val one = AmeaTag(UUID.randomUUID(), "Deaf", "Suitable for deaf visitors", true)
        val two = AmeaTag(UUID.randomUUID(), "Autistic", "Suitable for autistic visitors", false)
        persistence.saveOneNew(one)
        persistence.saveOneNew(two)

        // Act
        val all = persistence.getAll()

        // Assert
        assertThat(all).extracting<String> { it.name }
            .contains("Deaf", "Autistic")
    }

    // UPDATE
    @Test
    fun `updateOne updates persisted entry fields`() {
        // Arrange
        val original = AmeaTag(UUID.randomUUID(), "Mobility", "Mobility assistance", true)
        persistence.saveOneNew(original)

        // Act
        val updated = AmeaTag(original.id, "Mobility Updated", "Mobility assistance improved", false)
        val result = persistence.updateOne(updated)

        // Assert
        assertThat(result.id).isEqualTo(original.id)
        assertThat(result.name).isEqualTo("Mobility Updated")
        assertThat(result.description).isEqualTo("Mobility assistance improved")
        assertThat(result.active).isFalse()
    }

    // DELETE
    @Test
    fun `deleteOne removes the tag`() {
        // Arrange
        val id = UUID.randomUUID()
        val tag = AmeaTag(id, "Temp Tag", "To be deleted", true)
        persistence.saveOneNew(tag)
        // ensure present
        assertThat(persistence.getAll().any { it.id == id }).isTrue()

        // Act
        persistence.deleteOne(id)

        // Assert
        assertThat(persistence.getAll().any { it.id == id }).isFalse()
    }

    /** Edge cases **/
    @Test
    fun `getAll on empty db returns empty list`() {
        val all = persistence.getAll()
        assertThat(all).isEmpty()
    }

    @Test
    fun `saveOneNew accepts max-length fields`() {
        val name35 = "x".repeat(35)
        val desc250 = "y".repeat(250)
        val saved = persistence.saveOneNew(AmeaTag(UUID.randomUUID(), name35, desc250, true))
        assertThat(saved.name).hasSize(35)
        assertThat(saved.description).hasSize(250)
    }

    // deleteOne is a no-op on non-existing ID (CrudRepository.deleteById is idempotent)
    @Test
    fun `deleteOne on non-existing id is no-op`() {
        val before = persistence.getAll().count()
        persistence.deleteOne(UUID.randomUUID())
        val after = persistence.getAll().count()
        assertThat(after).isEqualTo(before)
    }

    @Test
    fun `saveOneNew rejects too-long fields`() {
        val name36 = "x".repeat(36)
        val tag = AmeaTag(UUID.randomUUID(), name36, "ok".repeat(2), true)
        org.junit.jupiter.api.assertThrows<org.springframework.dao.DataIntegrityViolationException> {
            // Save and then trigger a flush via a query to let the DB constraint raise the violation
            persistence.saveOneNew(tag)
            persistence.getAll()
        }
    }
}
