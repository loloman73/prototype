package xenagos.adapter.output.persistence.admin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import xenagos.adapter.output.persistence.BasePersistenceIT
import xenagos.adapter.output.persistence.admin.mapper.AdminTopicTagJPAMapper
import xenagos.domain.model.TopicTag
import java.util.UUID

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(AdminTopicTagJPAMapper::class, AdminTopicTagsPersistence::class)
open class AdminTopicTagsPersistenceIT : BasePersistenceIT() {

    @Autowired
    lateinit var persistence: AdminTopicTagsPersistence

    /** Happy path tests **/
    // CREATE
    @Test
    fun `saveOneNew persists a new entry and returns it`() {
        // Arrange
        val newEntry = TopicTag(
            UUID.randomUUID(),
            "Architecture",
            "Related to buildings and structures",
            true
        )

        // Act
        val saved = persistence.saveOneNew(newEntry)

        // Assert
        assertThat(saved.id).isEqualTo(newEntry.id)
        assertThat(saved.tagName).isEqualTo("Architecture")
        assertThat(saved.description).isEqualTo("Related to buildings and structures")
        assertThat(saved.active).isTrue()
    }

    // READ
    @Test
    fun `getAll returns persisted entries`() {
        // Arrange
        val one = TopicTag(UUID.randomUUID(), "Religion", "Religious topics", true)
        val two = TopicTag(UUID.randomUUID(), "Technology", "Tech topics", false)
        persistence.saveOneNew(one)
        persistence.saveOneNew(two)

        // Act
        val all = persistence.getAll()

        // Assert
        assertThat(all).extracting<String> { it.tagName }
            .contains("Religion", "Technology")
    }

    // UPDATE
    @Test
    fun `updateOne updates persisted entry fields`() {
        // Arrange
        val original = TopicTag(UUID.randomUUID(), "Arts", "About arts", true)
        persistence.saveOneNew(original)

        // Act
        val updated = TopicTag(original.id, "Arts Updated", "About arts and culture", false)
        val result = persistence.updateOne(updated)

        // Assert
        assertThat(result.id).isEqualTo(original.id)
        assertThat(result.tagName).isEqualTo("Arts Updated")
        assertThat(result.description).isEqualTo("About arts and culture")
        assertThat(result.active).isFalse()
    }

    // DELETE
    @Test
    fun `deleteOne removes the entry`() {
        // Arrange
        val id = UUID.randomUUID()
        val entry = TopicTag(id, "Temp Topic", "Temporary", true)
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
        val name35 = "x".repeat(35)
        val desc250 = "y".repeat(250)
        val saved = persistence.saveOneNew(TopicTag(UUID.randomUUID(), name35, desc250, true))
        assertThat(saved.tagName).hasSize(35)
        assertThat(saved.description).hasSize(250)
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
    fun `saveOneNew rejects too-long name`() {
        val name36 = "x".repeat(36)
        val entry = TopicTag(UUID.randomUUID(), name36, "ok description", true)
        org.junit.jupiter.api.assertThrows<org.springframework.dao.DataIntegrityViolationException> {
            // Save and then trigger a flush via a query to let the DB constraint raise the violation
            persistence.saveOneNew(entry)
            // force flush
            persistence.getAll()
        }
    }

    @Test
    fun `saveOneNew rejects too-long description`() {
        val desc251 = "y".repeat(251)
        val entry = TopicTag(UUID.randomUUID(), "Valid Name", desc251, true)
        org.junit.jupiter.api.assertThrows<org.springframework.dao.DataIntegrityViolationException> {
            persistence.saveOneNew(entry)
            // force flush
            persistence.getAll()
        }
    }
}
