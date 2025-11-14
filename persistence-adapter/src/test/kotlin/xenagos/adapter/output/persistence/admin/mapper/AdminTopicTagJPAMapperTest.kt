package xenagos.adapter.output.persistence.admin.mapper

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import xenagos.adapter.output.persistence.model.TopicTagJpaEntity
import xenagos.domain.model.TopicTag
import java.util.UUID

//The test suite includes:
// 1. Basic mapping tests for both directions
// 2. Active/inactive status tests
// 3. Various topic categories (History, Art, Architecture, Nature, etc.)
// 4. Longer descriptions to test field capacity
// 5. Special characters in tag names (e.g., "Food & Drink")
// 6. Roundtrip conversion tests for data integrity
// 7. Multiple test cases with different topic categories
// 8. Minimal descriptions to test edge cases
// 9. Null handling tests for all required fields (addressing the TODO comment)


class AdminTopicTagJPAMapperTest {

    private val mapper = AdminTopicTagJPAMapper()

    @Test
    fun `toJpaEntity should map domain entity to JPA entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = TopicTag(
            id = id,
            tagName = "History",
            description = "Historical sites and monuments",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertEquals(id, jpaEntity.id)
        assertEquals("History", jpaEntity.topicTag)
        assertEquals("Historical sites and monuments", jpaEntity.description)
        assertTrue(jpaEntity.active!!)
    }

    @Test
    fun `toJpaEntity should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = TopicTag(
            id = id,
            tagName = "Art",
            description = "Art galleries and museums",
            active = false
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertEquals(id, jpaEntity.id)
        assertEquals("Art", jpaEntity.topicTag)
        assertEquals("Art galleries and museums", jpaEntity.description)
        assertFalse(jpaEntity.active!!)
    }

    @Test
    fun `toJpaEntity should handle various topic categories`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = TopicTag(
            id = id,
            tagName = "Architecture",
            description = "Architectural landmarks and buildings",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertEquals(id, jpaEntity.id)
        assertEquals("Architecture", jpaEntity.topicTag)
        assertEquals("Architectural landmarks and buildings", jpaEntity.description)
        assertTrue(jpaEntity.active!!)
    }

    @Test
    fun `toDomainEntity should map JPA entity to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val jpaEntity = TopicTagJpaEntity().apply {
            this.id = id
            this.topicTag = "Nature"
            this.description = "Natural parks and landscapes"
            this.active = true
        }

        // When
        val domainEntity = mapper.toDomainEntity(jpaEntity)

        // Then
        assertEquals(id, domainEntity.id)
        assertEquals("Nature", domainEntity.tagName)
        assertEquals("Natural parks and landscapes", domainEntity.description)
        assertTrue(domainEntity.active)
    }

    @Test
    fun `toDomainEntity should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val jpaEntity = TopicTagJpaEntity().apply {
            this.id = id
            this.topicTag = "Culture"
            this.description = "Cultural heritage and traditions"
            this.active = false
        }

        // When
        val domainEntity = mapper.toDomainEntity(jpaEntity)

        // Then
        assertEquals(id, domainEntity.id)
        assertEquals("Culture", domainEntity.tagName)
        assertEquals("Cultural heritage and traditions", domainEntity.description)
        assertFalse(domainEntity.active)
    }

    @Test
    fun `roundtrip conversion should preserve data`() {
        // Given
        val id = UUID.randomUUID()
        val originalDomain = TopicTag(
            id = id,
            tagName = "Religion",
            description = "Religious sites and sacred places",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(originalDomain)
        val resultDomain = mapper.toDomainEntity(jpaEntity)

        // Then
        assertEquals(originalDomain.id, resultDomain.id)
        assertEquals(originalDomain.tagName, resultDomain.tagName)
        assertEquals(originalDomain.description, resultDomain.description)
        assertEquals(originalDomain.active, resultDomain.active)
    }

    @Test
    fun `toJpaEntity should handle tags with longer descriptions`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = TopicTag(
            id = id,
            tagName = "Science",
            description = "Scientific museums, observatories, planetariums, and other science-related attractions",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertEquals(id, jpaEntity.id)
        assertEquals("Science", jpaEntity.topicTag)
        assertEquals("Scientific museums, observatories, planetariums, and other science-related attractions", jpaEntity.description)
        assertTrue(jpaEntity.active!!)
    }

    @Test
    fun `toJpaEntity should handle tags with special characters in name`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = TopicTag(
            id = id,
            tagName = "Food & Drink",
            description = "Culinary experiences and local cuisine",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertEquals(id, jpaEntity.id)
        assertEquals("Food & Drink", jpaEntity.topicTag)
        assertEquals("Culinary experiences and local cuisine", jpaEntity.description)
        assertTrue(jpaEntity.active!!)
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when id is null`() {
        // Given
        val jpaEntity = TopicTagJpaEntity().apply {
            this.id = null
            this.topicTag = "History"
            this.description = "Historical sites"
            this.active = true
        }

        // When & Then
        assertThrows(NullPointerException::class.java) {
            mapper.toDomainEntity(jpaEntity)
        }
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when topicTag is null`() {
        // Given
        val jpaEntity = TopicTagJpaEntity().apply {
            this.id = UUID.randomUUID()
            this.topicTag = null
            this.description = "Historical sites"
            this.active = true
        }

        // When & Then
        assertThrows(NullPointerException::class.java) {
            mapper.toDomainEntity(jpaEntity)
        }
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when description is null`() {
        // Given
        val jpaEntity = TopicTagJpaEntity().apply {
            this.id = UUID.randomUUID()
            this.topicTag = "History"
            this.description = null
            this.active = true
        }

        // When & Then
        assertThrows(NullPointerException::class.java) {
            mapper.toDomainEntity(jpaEntity)
        }
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when active is null`() {
        // Given
        val jpaEntity = TopicTagJpaEntity().apply {
            this.id = UUID.randomUUID()
            this.topicTag = "History"
            this.description = "Historical sites"
            this.active = null
        }

        // When & Then
        assertThrows(NullPointerException::class.java) {
            mapper.toDomainEntity(jpaEntity)
        }
    }

    @Test
    fun `toJpaEntity should handle multiple topic categories`() {
        // Given
        val testCases = listOf(
            Triple("Museums", "Museums and exhibitions", true),
            Triple("Parks", "Parks and gardens", false),
            Triple("Sports", "Sports venues and activities", true),
            Triple("Shopping", "Shopping areas and markets", false),
            Triple("Entertainment", "Entertainment venues and events", true)
        )

        testCases.forEach { (tagName, description, active) ->
            val id = UUID.randomUUID()
            val domainEntity = TopicTag(id, tagName, description, active)

            // When
            val jpaEntity = mapper.toJpaEntity(domainEntity)

            // Then
            assertEquals(id, jpaEntity.id)
            assertEquals(tagName, jpaEntity.topicTag)
            assertEquals(description, jpaEntity.description)
            assertEquals(active, jpaEntity.active)
        }
    }

    @Test
    fun `roundtrip conversion should preserve data for inactive tag`() {
        // Given
        val id = UUID.randomUUID()
        val originalDomain = TopicTag(
            id = id,
            tagName = "Nightlife",
            description = "Bars, clubs, and evening entertainment",
            active = false
        )

        // When
        val jpaEntity = mapper.toJpaEntity(originalDomain)
        val resultDomain = mapper.toDomainEntity(jpaEntity)

        // Then
        assertEquals(originalDomain.id, resultDomain.id)
        assertEquals(originalDomain.tagName, resultDomain.tagName)
        assertEquals(originalDomain.description, resultDomain.description)
        assertEquals(originalDomain.active, resultDomain.active)
        assertFalse(resultDomain.active)
    }

    @Test
    fun `toJpaEntity should handle tags with minimal description`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = TopicTag(
            id = id,
            tagName = "Beach",
            description = "Beaches",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertEquals(id, jpaEntity.id)
        assertEquals("Beach", jpaEntity.topicTag)
        assertEquals("Beaches", jpaEntity.description)
        assertTrue(jpaEntity.active!!)
    }
}