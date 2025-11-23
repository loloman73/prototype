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

    @Test
    fun `saveOneNew persists a new accessibility tag and returns it`() {
        val newTag = AccessibilityTag(
            UUID.randomUUID(),
            "Wheelchair Accessible",
            "Suitable for wheelchair users",
            true
        )

        val saved = persistence.saveOneNew(newTag)

        assertThat(saved.id).isEqualTo(newTag.id)
        assertThat(saved.name).isEqualTo("Wheelchair Accessible")
        assertThat(saved.description).isEqualTo("Suitable for wheelchair users")
        assertThat(saved.active).isTrue()
    }

    @Test
    fun `getAll returns persisted tags`() {
        val one = AccessibilityTag(UUID.randomUUID(), "Visual Aid", "Visual assistance", true)
        val two = AccessibilityTag(UUID.randomUUID(), "Hearing Aid", "Audio assistance", false)

        persistence.saveOneNew(one)
        persistence.saveOneNew(two)

        val all = persistence.getAll()

        assertThat(all).extracting<String> { it.name }
            .contains("Visual Aid", "Hearing Aid")
    }

    @Test
    fun `updateOne updates persisted tag fields`() {
        val original = AccessibilityTag(UUID.randomUUID(), "Signage", "Basic signage", true)
        persistence.saveOneNew(original)

        val updated = AccessibilityTag(original.id, "Signage Updated", "Improved signage", false)
        val result = persistence.updateOne(updated)

        assertThat(result.id).isEqualTo(original.id)
        assertThat(result.name).isEqualTo("Signage Updated")
        assertThat(result.description).isEqualTo("Improved signage")
        assertThat(result.active).isFalse()
    }

    @Test
    fun `deleteOne removes the tag`() {
        val id = UUID.randomUUID()
        val tag = AccessibilityTag(id, "Temp Tag", "To be deleted", true)
        persistence.saveOneNew(tag)

        // ensure present
        assertThat(persistence.getAll().any { it.id == id }).isTrue()

        persistence.deleteOne(id)

        assertThat(persistence.getAll().any { it.id == id }).isFalse()
    }
}