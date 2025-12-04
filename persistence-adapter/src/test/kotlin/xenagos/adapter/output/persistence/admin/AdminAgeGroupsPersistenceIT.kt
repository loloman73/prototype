package xenagos.adapter.output.persistence.admin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import xenagos.adapter.output.persistence.BasePersistenceIT
import xenagos.adapter.output.persistence.admin.mapper.AdminAgeGroupJPAMapper
import xenagos.domain.model.AgeGroup
import java.util.UUID

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(AdminAgeGroupJPAMapper::class, AdminAgeGroupsPersistence::class)
open class AdminAgeGroupsPersistenceIT : BasePersistenceIT() {

    @Autowired
    lateinit var persistence: AdminAgeGroupsPersistence

    /** Happy path tests **/
    // CREATE
    @Test
    fun `saveOneNew persists a new entry and returns it`() {
        // Arrange
        val newEntry = AgeGroup(
            UUID.randomUUID(),
            "Teenagers",
            13.toByte(),
            17.toByte(),
            true
        )

        // Act
        val saved = persistence.saveOneNew(newEntry)

        // Assert
        assertThat(saved.id).isEqualTo(newEntry.id)
        assertThat(saved.name).isEqualTo("Teenagers")
        assertThat(saved.minAge).isEqualTo(13)
        assertThat(saved.maxAge).isEqualTo(17)
        assertThat(saved.active).isTrue()
    }

    // READ
    @Test
    fun `getAll returns persisted entries`() {
        // Arrange
        val one = AgeGroup(UUID.randomUUID(), "Preteens", 10.toByte(), 12.toByte(), true)
        val two = AgeGroup(UUID.randomUUID(), "Young Adults", 18.toByte(), 29.toByte(), false)
        persistence.saveOneNew(one)
        persistence.saveOneNew(two)

        // Act
        val all = persistence.getAll()

        // Assert
        assertThat(all).extracting<String> { it.name }
            .contains("Preteens", "Young Adults")
    }

    // UPDATE
    @Test
    fun `updateOne updates persisted entry fields`() {
        // Arrange
        val original = AgeGroup(UUID.randomUUID(), "Middle Age", 30.toByte(), 64.toByte(), true)
        persistence.saveOneNew(original)

        // Act
        val updated = AgeGroup(original.id, "Middle Age Updated", 31.toByte(), 60.toByte(), false)
        val result = persistence.updateOne(updated)

        // Assert
        assertThat(result.id).isEqualTo(original.id)
        assertThat(result.name).isEqualTo("Middle Age Updated")
        assertThat(result.minAge).isEqualTo(31)
        assertThat(result.maxAge).isEqualTo(60)
        assertThat(result.active).isFalse()
    }

    // DELETE
    @Test
    fun `deleteOne removes the entry`() {
        // Arrange
        val id = UUID.randomUUID()
        val entry = AgeGroup(id, "Temp Group", 6.toByte(), 9.toByte(), true)
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
    fun `saveOneNew accepts max-length groupName`() {
        val name35 = "x".repeat(35)
        val saved = persistence.saveOneNew(AgeGroup(UUID.randomUUID(), name35, 6.toByte(), 9.toByte(), true))
        assertThat(saved.name).hasSize(35)
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
    fun `saveOneNew rejects too-long groupName`() {
        val name36 = "x".repeat(36)
        val entry = AgeGroup(UUID.randomUUID(), name36, 6.toByte(), 9.toByte(), true)
        org.junit.jupiter.api.assertThrows<org.springframework.dao.DataIntegrityViolationException> {
            // Save and then trigger a flush via a query to let the DB constraint raise the violation
            // Hibernate flushes before queries; this will force the insert and surface the exception
            persistence.saveOneNew(entry)
            persistence.getAll()
        }
    }
}
