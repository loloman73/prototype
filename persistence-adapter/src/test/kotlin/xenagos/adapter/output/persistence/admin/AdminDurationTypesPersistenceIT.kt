package xenagos.adapter.output.persistence.admin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import xenagos.adapter.output.persistence.BasePersistenceIT
import xenagos.adapter.output.persistence.admin.mapper.AdminDurationTypeJPAMapper
import xenagos.domain.model.DurationType
import java.util.UUID

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(AdminDurationTypeJPAMapper::class, AdminDurationTypesPersistence::class)
open class AdminDurationTypesPersistenceIT : BasePersistenceIT() {

    @Autowired
    lateinit var persistence: AdminDurationTypesPersistence

    /** Happy path tests **/
    // CREATE
    @Test
    fun `saveOneNew persists a new entry and returns it`() {
        // Arrange
        val newEntry = DurationType(
            UUID.randomUUID(),
            "Short Walk",
            true
        )

        // Act
        val saved = persistence.saveOneNew(newEntry)

        // Assert
        assertThat(saved.id).isEqualTo(newEntry.id)
        assertThat(saved.type).isEqualTo("Short Walk")
        assertThat(saved.active).isTrue()
    }

    // READ
    @Test
    fun `getAll returns persisted entries`() {
        // Arrange
        val one = DurationType(UUID.randomUUID(), "Half Day", true)
        val two = DurationType(UUID.randomUUID(), "Full Day", false)
        persistence.saveOneNew(one)
        persistence.saveOneNew(two)

        // Act
        val all = persistence.getAll()

        // Assert
        assertThat(all).extracting<String> { it.type }
            .contains("Half Day", "Full Day")
    }

    // UPDATE
    @Test
    fun `updateOne updates persisted entry fields`() {
        // Arrange
        val original = DurationType(UUID.randomUUID(), "Old Label", true)
        persistence.saveOneNew(original)

        // Act
        val updated = DurationType(original.id, "New Label", false)
        val result = persistence.updateOne(updated)

        // Assert
        assertThat(result.id).isEqualTo(original.id)
        assertThat(result.type).isEqualTo("New Label")
        assertThat(result.active).isFalse()
    }

    // DELETE
    @Test
    fun `deleteOne removes the entry`() {
        // Arrange
        val id = UUID.randomUUID()
        val entry = DurationType(id, "Temp Duration", true)
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
        val saved = persistence.saveOneNew(DurationType(UUID.randomUUID(), type35, true))
        assertThat(saved.type).hasSize(35)
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
    fun `saveOneNew rejects too-long type`() {
        val type36 = "x".repeat(36)
        val entry = DurationType(UUID.randomUUID(), type36, true)
        org.junit.jupiter.api.assertThrows<org.springframework.dao.DataIntegrityViolationException> {
            // Save and then trigger a flush via a query to let the DB constraint raise the violation
            // Hibernate flushes before queries; this will force the insert and surface the exception
            persistence.saveOneNew(entry)
            persistence.getAll()
        }
    }
}
