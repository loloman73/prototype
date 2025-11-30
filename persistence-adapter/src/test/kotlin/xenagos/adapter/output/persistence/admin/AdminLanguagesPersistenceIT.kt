package xenagos.adapter.output.persistence.admin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import xenagos.adapter.output.persistence.BasePersistenceIT
import xenagos.adapter.output.persistence.admin.mapper.AdminLanguageJPAMapper
import xenagos.domain.model.Language
import java.util.UUID

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(AdminLanguageJPAMapper::class, AdminLanguagesPersistence::class)
open class AdminLanguagesPersistenceIT : BasePersistenceIT() {

    @Autowired
    lateinit var persistence: AdminLanguagesPersistence

    /** Happy path tests **/
    // CREATE
    @Test
    fun `saveOneNew persists a new entry and returns it`() {
        // Arrange
        val newEntry = Language(
            UUID.randomUUID(),
            "ENG",
            "English",
            "English",
            true
        )

        // Act
        val saved = persistence.saveOneNew(newEntry)

        // Assert
        assertThat(saved.id).isEqualTo(newEntry.id)
        assertThat(saved.code).isEqualTo("ENG")
        assertThat(saved.englishName).isEqualTo("English")
        assertThat(saved.nativeName).isEqualTo("English")
        assertThat(saved.active).isTrue()
    }

    // READ
    @Test
    fun `getAll returns persisted entries`() {
        // Arrange
        val one = Language(UUID.randomUUID(), "ELL", "Greek", "Ελληνικά", true)
        val two = Language(UUID.randomUUID(), "DEU", "German", "Deutsch", false)
        persistence.saveOneNew(one)
        persistence.saveOneNew(two)

        // Act
        val all = persistence.getAll()

        // Assert
        assertThat(all).extracting<String> { it.code }
            .contains("ELL", "DEU")
    }

    // UPDATE
    @Test
    fun `updateOne updates persisted entry fields`() {
        // Arrange
        val original = Language(UUID.randomUUID(), "FRA", "French", "Français", true)
        persistence.saveOneNew(original)

        // Act
        val updated = Language(original.id, "FRA", "French Updated", "Français MAJ", false)
        val result = persistence.updateOne(updated)

        // Assert
        assertThat(result.id).isEqualTo(original.id)
        assertThat(result.englishName).isEqualTo("French Updated")
        assertThat(result.nativeName).isEqualTo("Français MAJ")
        assertThat(result.active).isFalse()
    }

    // DELETE
    @Test
    fun `deleteOne removes the entry`() {
        // Arrange
        val id = UUID.randomUUID()
        val entry = Language(id, "SPA", "Spanish", "Español", true)
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
    fun `saveOneNew accepts max-length fields`() {
        val english35 = "e".repeat(35)
        val native35 = "n".repeat(35)
        val saved = persistence.saveOneNew(Language(UUID.randomUUID(), "ITA", english35, native35, true))
        assertThat(saved.englishName).hasSize(35)
        assertThat(saved.nativeName).hasSize(35)
    }

    @Test
    fun `saveOneNew rejects too-long names`() {
        val english36 = "e".repeat(36)
        val entry = Language(UUID.randomUUID(), "POR", english36, "Português", true)
        org.junit.jupiter.api.assertThrows<org.springframework.dao.DataIntegrityViolationException> {
            persistence.saveOneNew(entry)
            // force flush
            persistence.getAll()
        }
    }

    @Test
    fun `saveOneNew rejects code longer than 3`() {
        val entry = Language(UUID.randomUUID(), "ENUS", "English US", "English US", true)
        org.junit.jupiter.api.assertThrows<org.springframework.dao.DataIntegrityViolationException> {
            persistence.saveOneNew(entry)
            // force flush
            persistence.getAll()
        }
    }

    // deleteOne is a no-op on non-existing ID
    @Test
    fun `deleteOne on non-existing id is no-op`() {
        val before = persistence.getAll().count()
        persistence.deleteOne(UUID.randomUUID())
        val after = persistence.getAll().count()
        assertThat(after).isEqualTo(before)
    }
}
