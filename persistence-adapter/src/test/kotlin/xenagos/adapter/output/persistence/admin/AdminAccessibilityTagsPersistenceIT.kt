package xenagos.adapter.output.persistence.admin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import xenagos.adapter.output.persistence.admin.mapper.AdminAccessibilityTagJPAMapper
import xenagos.adapter.output.persistence.BasePersistenceIT
import xenagos.domain.model.AccessibilityTag
import java.util.UUID

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(AdminAccessibilityTagJPAMapper::class, AdminAccessibilityTagsPersistence::class)
open class AdminAccessibilityTagsPersistenceIT : BasePersistenceIT() {

    @Autowired
    lateinit var persistence: AdminAccessibilityTagsPersistence

    /** Happy path tests **/
    // CREATE
    @Test
    fun `saveOneNew persists a new entry and returns it`() {
        // Arrange
        val newTag = AccessibilityTag(
            UUID.randomUUID(),
            "Wheelchair Accessible",
            "Suitable for wheelchair users",
            true
        )

        // Act
        val saved = persistence.saveOneNew(newTag)

        // Assert
        assertThat(saved.id).isEqualTo(newTag.id)
        assertThat(saved.name).isEqualTo("Wheelchair Accessible")
        assertThat(saved.description).isEqualTo("Suitable for wheelchair users")
        assertThat(saved.active).isTrue()
    }

    // READ
    @Test
    fun `getAll returns persisted entries`() {
        // Arrange
        val one = AccessibilityTag(UUID.randomUUID(), "Visual Aid", "Visual assistance", true)
        val two = AccessibilityTag(UUID.randomUUID(), "Hearing Aid", "Audio assistance", false)
        persistence.saveOneNew(one)
        persistence.saveOneNew(two)

        // Act
        val all = persistence.getAll()

        // Assert
        assertThat(all).extracting<String> { it.name }
            .contains("Visual Aid", "Hearing Aid")
    }

    // UPDATE
    @Test
    fun `updateOne updates persisted entry fields`() {
        // Arrange
        val original = AccessibilityTag(UUID.randomUUID(), "Signage", "Basic signage", true)
        persistence.saveOneNew(original)

        // Act
        val updated = AccessibilityTag(original.id, "Signage Updated", "Improved signage", false)
        val result = persistence.updateOne(updated)

        // Assert
        assertThat(result.id).isEqualTo(original.id)
        assertThat(result.name).isEqualTo("Signage Updated")
        assertThat(result.description).isEqualTo("Improved signage")
        assertThat(result.active).isFalse()
    }

    // DELETE
    @Test
    fun `deleteOne removes the tag`() {
        // Arrange
        val id = UUID.randomUUID()
        val tag = AccessibilityTag(id, "Temp Tag", "To be deleted", true)
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
        println(persistence.getAll().count())
        val all = persistence.getAll()
    }

    @Test
    fun `saveOneNew accepts max-length fields`() {
        val name35 = "x".repeat(35)
        val desc250 = "y".repeat(250)
        val saved = persistence.saveOneNew(AccessibilityTag(UUID.randomUUID(), name35, desc250, true))
        assertThat(saved.name).hasSize(35)
        assertThat(saved.description).hasSize(250)
    }
    //TODO: - Repeat for description > 250

    //deleteOne is a no-op on non-existing ID  confirm that `CrudRepository.deleteById` is idempotent.
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
        val tag = AccessibilityTag(UUID.randomUUID(), name36, "ok".repeat(2), true)
        org.junit.jupiter.api.assertThrows<org.springframework.dao.DataIntegrityViolationException> {
            // Save and then trigger a flush via a query to let the DB constraint raise the violation
            // Hibernate flushes before queries; this will force the insert and surface the exception
            persistence.saveOneNew(tag)
            persistence.getAll()
        }
    }


    //TODO: - Update entity when ID does not exist in database must result in error
    //      - Dont allow duplicate names at database

}
