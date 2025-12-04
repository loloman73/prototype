package xenagos.adapter.output.persistence.admin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import xenagos.adapter.output.persistence.BasePersistenceIT
import xenagos.adapter.output.persistence.admin.mapper.AdminMediaTypeJPAMapper
import xenagos.domain.model.MediaType
import java.util.UUID

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(AdminMediaTypeJPAMapper::class, AdminMediaTypesPersistence::class)
open class AdminMediaTypesPersistenceIT : BasePersistenceIT() {

    @Autowired
    lateinit var persistence: AdminMediaTypesPersistence

    /** Happy path tests **/
    // CREATE
    @Test
    fun `saveOneNew persists a new entry and returns it`() {
        // Arrange
        val newEntry = MediaType(
            UUID.randomUUID(),
            "Audio Guide",
            true
        )

        // Act
        val saved = persistence.saveOneNew(newEntry)

        // Assert
        assertThat(saved.id).isEqualTo(newEntry.id)
        assertThat(saved.name).isEqualTo("Audio Guide")
        assertThat(saved.active).isTrue()
    }

    // READ
    @Test
    fun `getAll returns persisted entries`() {
        // Arrange
        val one = MediaType(UUID.randomUUID(), "Audio", true)
        val two = MediaType(UUID.randomUUID(), "Visual", false)
        persistence.saveOneNew(one)
        persistence.saveOneNew(two)

        // Act
        val all = persistence.getAll()

        // Assert
        assertThat(all).extracting<String> { it.name }
            .contains("Audio", "Visual")
    }

    // UPDATE
    @Test
    fun `updateOne updates persisted entry fields`() {
        // Arrange
        val original = MediaType(UUID.randomUUID(), "Original Type", true)
        persistence.saveOneNew(original)

        // Act
        val updated = MediaType(original.id, "Updated Type", false)
        val result = persistence.updateOne(updated)

        // Assert
        assertThat(result.id).isEqualTo(original.id)
        assertThat(result.name).isEqualTo("Updated Type")
        assertThat(result.active).isFalse()
    }

    // DELETE
    @Test
    fun `deleteOne removes the entry`() {
        // Arrange
        val id = UUID.randomUUID()
        val entry = MediaType(id, "Temp Media", true)
        persistence.saveOneNew(entry)
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
    fun `saveOneNew accepts max-length type`() {
        val type35 = "x".repeat(35)
        val saved = persistence.saveOneNew(MediaType(UUID.randomUUID(), type35, true))
        assertThat(saved.name).hasSize(35)
    }

    // deleteOne is a no-op on non-existing ID
    @Test
    fun `deleteOne on non-existing id is no-op`() {
        val before = persistence.getAll().count()
        persistence.deleteOne(UUID.randomUUID())
        val after = persistence.getAll().count()
        assertThat(after).isEqualTo(before)
    }

    @Test
    fun `saveOneNew rejects too-long type`() {
        val type36 = "x".repeat(36)
        val entry = MediaType(UUID.randomUUID(), type36, true)
        org.junit.jupiter.api.assertThrows<org.springframework.dao.DataIntegrityViolationException> {
            // Save and then trigger a flush via a query to let the DB constraint raise the violation
            persistence.saveOneNew(entry)
            // Hibernate flushes before queries; this will force the insert and surface the exception
            persistence.getAll()
        }
    }
}
