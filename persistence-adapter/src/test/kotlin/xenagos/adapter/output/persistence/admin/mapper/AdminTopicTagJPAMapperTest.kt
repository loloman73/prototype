
package xenagos.adapter.output.persistence.admin.mapper

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import xenagos.adapter.output.persistence.model.TopicTagJpaEntity
import xenagos.domain.model.TopicTag
import java.util.UUID

// This unit test includes:
// Positive test cases: Testing both toJpaEntity and toDomainEntity with valid data
// Active/Inactive status: Testing both true and false values for the active field
// Various topic tags: Testing different museum/cultural topic categories
// Description handling: Testing various description lengths and content
// Roundtrip conversion: Ensuring data integrity when converting domain → JPA → domain
// Null handling tests: Testing the TODO comment for all required fields
// Multiple tags: Testing batch conversion of multiple topic tags

class AdminTopicTagJPAMapperTest {

    private val mapper = AdminTopicTagJPAMapper()

    @Test
    fun `toJpaEntity should map domain entity to JPA entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = TopicTag(
            id = id,
            tagName = "Ancient History",
            description = "Topics related to ancient civilizations and historical periods",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.topicTag).isEqualTo("Ancient History")
        assertThat(jpaEntity.description).isEqualTo("Topics related to ancient civilizations and historical periods")
        assertThat(jpaEntity.active).isTrue()
    }

    @Test
    fun `toJpaEntity should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = TopicTag(
            id = id,
            tagName = "Modern Art",
            description = "Contemporary art movements and styles from the 20th century onwards",
            active = false
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.topicTag).isEqualTo("Modern Art")
        assertThat(jpaEntity.description).isEqualTo("Contemporary art movements and styles from the 20th century onwards")
        assertThat(jpaEntity.active).isFalse()
    }

    @Test
    fun `toJpaEntity should handle various museum topic categories`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = TopicTag(
            id = id,
            tagName = "Natural Sciences",
            description = "Biology, geology, paleontology and other natural science topics",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.topicTag).isEqualTo("Natural Sciences")
        assertThat(jpaEntity.description).isEqualTo("Biology, geology, paleontology and other natural science topics")
        assertThat(jpaEntity.active).isTrue()
    }

    @Test
    fun `toJpaEntity should handle short descriptions`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = TopicTag(
            id = id,
            tagName = "Architecture",
            description = "Building design and construction",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.topicTag).isEqualTo("Architecture")
        assertThat(jpaEntity.description).isEqualTo("Building design and construction")
    }

    @Test
    fun `toJpaEntity should handle long descriptions`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = TopicTag(
            id = id,
            tagName = "Cultural Heritage",
            description = "Preservation and interpretation of cultural artifacts, traditions, customs, and practices that have been passed down through generations and represent the collective identity of communities",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.topicTag).isEqualTo("Cultural Heritage")
        assertThat(jpaEntity.description).hasSize(189)
        assertThat(jpaEntity.description).startsWith("Preservation and interpretation")
    }

    @Test
    fun `toDomainEntity should map JPA entity to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val jpaEntity = TopicTagJpaEntity().apply {
            this.id = id
            this.topicTag = "Archaeology"
            this.description = "Study of human history through excavation and analysis of artifacts"
            this.active = true
        }

        // When
        val domainEntity = mapper.toDomainEntity(jpaEntity)

        // Then
        assertThat(domainEntity.id).isEqualTo(id)
        assertThat(domainEntity.tagName).isEqualTo("Archaeology")
        assertThat(domainEntity.description).isEqualTo("Study of human history through excavation and analysis of artifacts")
        assertThat(domainEntity.active).isTrue()
    }

    @Test
    fun `toDomainEntity should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val jpaEntity = TopicTagJpaEntity().apply {
            this.id = id
            this.topicTag = "Renaissance"
            this.description = "European cultural movement from the 14th to 17th century"
            this.active = false
        }

        // When
        val domainEntity = mapper.toDomainEntity(jpaEntity)

        // Then
        assertThat(domainEntity.id).isEqualTo(id)
        assertThat(domainEntity.tagName).isEqualTo("Renaissance")
        assertThat(domainEntity.description).isEqualTo("European cultural movement from the 14th to 17th century")
        assertThat(domainEntity.active).isFalse()
    }

    @Test
    fun `roundtrip conversion should preserve data`() {
        // Given
        val id = UUID.randomUUID()
        val originalDomain = TopicTag(
            id = id,
            tagName = "Classical Music",
            description = "Western art music from the Medieval period to the present day",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(originalDomain)
        val resultDomain = mapper.toDomainEntity(jpaEntity)

        // Then
        assertThat(resultDomain)
            .usingRecursiveComparison()
            .isEqualTo(originalDomain)
    }

    @Test
    fun `roundtrip conversion should preserve inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val originalDomain = TopicTag(
            id = id,
            tagName = "Baroque Period",
            description = "Art and music style from approximately 1600 to 1750",
            active = false
        )

        // When
        val jpaEntity = mapper.toJpaEntity(originalDomain)
        val resultDomain = mapper.toDomainEntity(jpaEntity)

        // Then
        assertThat(resultDomain)
            .usingRecursiveComparison()
            .isEqualTo(originalDomain)
        assertThat(resultDomain.active).isFalse()
    }

    @Test
    fun `toJpaEntity should handle multiple topic tags with different categories`() {
        // Given
        val topicTags = listOf(
            TopicTag(UUID.randomUUID(), "Mythology", "Ancient myths and legends from various cultures", true),
            TopicTag(UUID.randomUUID(), "Astronomy", "Study of celestial objects and phenomena", true),
            TopicTag(UUID.randomUUID(), "Medieval History", "European history from 5th to 15th century", false),
            TopicTag(UUID.randomUUID(), "Impressionism", "19th century art movement characterized by light and color", true)
        )

        // When
        val jpaEntities = topicTags.map { mapper.toJpaEntity(it) }

        // Then
        assertThat(jpaEntities).hasSize(4)
        assertThat(jpaEntities[0].topicTag).isEqualTo("Mythology")
        assertThat(jpaEntities[0].active).isTrue()
        assertThat(jpaEntities[1].topicTag).isEqualTo("Astronomy")
        assertThat(jpaEntities[2].topicTag).isEqualTo("Medieval History")
        assertThat(jpaEntities[2].active).isFalse()
        assertThat(jpaEntities[3].topicTag).isEqualTo("Impressionism")
    }

    @Test
    fun `toJpaEntity should handle topic tags with special characters`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = TopicTag(
            id = id,
            tagName = "20th & 21st Century",
            description = "Modern era (1900-present): technology, wars, and social changes",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.topicTag).isEqualTo("20th & 21st Century")
        assertThat(jpaEntity.topicTag).contains("&")
        assertThat(jpaEntity.description).contains(":")
    }

    @Test
    fun `toJpaEntity should handle topic tags with hyphens and parentheses`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = TopicTag(
            id = id,
            tagName = "Pre-Columbian Art",
            description = "Art and artifacts from Americas before Columbus (pre-1492)",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.topicTag).isEqualTo("Pre-Columbian Art")
        assertThat(jpaEntity.description).contains("(pre-1492)")
    }

    @Test
    fun `toJpaEntity should handle science and technology topics`() {
        // Given
        val scienceTopics = listOf(
            TopicTag(UUID.randomUUID(), "Physics", "Study of matter, energy, and their interactions", true),
            TopicTag(UUID.randomUUID(), "Biology", "Science of living organisms and life processes", true),
            TopicTag(UUID.randomUUID(), "Chemistry", "Study of substances and their properties and reactions", true)
        )

        // When
        val jpaEntities = scienceTopics.map { mapper.toJpaEntity(it) }

        // Then
        assertThat(jpaEntities).hasSize(3)
        assertThat(jpaEntities).extracting("topicTag")
            .containsExactly("Physics", "Biology", "Chemistry")
        assertThat(jpaEntities).allMatch { it.active == true }
    }

    @Test
    fun `toJpaEntity should handle art movement topics`() {
        // Given
        val artTopics = listOf(
            TopicTag(UUID.randomUUID(), "Cubism", "Early 20th century art movement with geometric forms", false),
            TopicTag(UUID.randomUUID(), "Surrealism", "Art movement exploring the unconscious mind", true),
            TopicTag(UUID.randomUUID(), "Abstract Expressionism", "Post-World War II American art movement", true)
        )

        // When
        val jpaEntities = artTopics.map { mapper.toJpaEntity(it) }

        // Then
        assertThat(jpaEntities).hasSize(3)
        assertThat(jpaEntities[0].topicTag).isEqualTo("Cubism")
        assertThat(jpaEntities[0].active).isFalse()
        assertThat(jpaEntities[1].topicTag).isEqualTo("Surrealism")
        assertThat(jpaEntities[2].topicTag).isEqualTo("Abstract Expressionism")
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when id is null`() {
        // Given
        val jpaEntity = TopicTagJpaEntity().apply {
            this.id = null
            this.topicTag = "History"
            this.description = "Study of past events"
            this.active = true
        }

        // When & Then
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when topicTag is null`() {
        // Given
        val jpaEntity = TopicTagJpaEntity().apply {
            this.id = UUID.randomUUID()
            this.topicTag = null
            this.description = "Study of past events"
            this.active = true
        }

        // When & Then
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
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
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when active is null`() {
        // Given
        val jpaEntity = TopicTagJpaEntity().apply {
            this.id = UUID.randomUUID()
            this.topicTag = "History"
            this.description = "Study of past events"
            this.active = null
        }

        // When & Then
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
    }

    @Test
    fun `toDomainEntity should map all fields correctly from JPA entity`() {
        // Given
        val id = UUID.randomUUID()
        val jpaEntity = TopicTagJpaEntity().apply {
            this.id = id
            this.topicTag = "Ethnography"
            this.description = "Study of cultures and peoples through observation and fieldwork"
            this.active = true
        }

        // When
        val domainEntity = mapper.toDomainEntity(jpaEntity)

        // Then
        assertThat(domainEntity)
            .extracting("id", "tagName", "description", "active")
            .containsExactly(
                id,
                "Ethnography",
                "Study of cultures and peoples through observation and fieldwork",
                true
            )
    }

    @Test
    fun `toJpaEntity should handle geographical and regional topics`() {
        // Given
        val regionalTopics = listOf(
            TopicTag(UUID.randomUUID(), "Mediterranean", "Art and culture from Mediterranean region", true),
            TopicTag(UUID.randomUUID(), "Asian Art", "Traditional and contemporary art from Asia", true),
            TopicTag(UUID.randomUUID(), "African Heritage", "Cultural heritage and artifacts from Africa", true)
        )

        // When
        val jpaEntities = regionalTopics.map { mapper.toJpaEntity(it) }

        // Then
        assertThat(jpaEntities).hasSize(3)
        assertThat(jpaEntities).extracting("topicTag")
            .containsExactly("Mediterranean", "Asian Art", "African Heritage")
    }

    @Test
    fun `toJpaEntity should handle historical period topics`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = TopicTag(
            id = id,
            tagName = "Victorian Era",
            description = "British history and culture during Queen Victoria's reign (1837-1901)",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.topicTag).isEqualTo("Victorian Era")
        assertThat(jpaEntity.description).contains("1837-1901")
    }

    @Test
    fun `roundtrip conversion should preserve special characters in descriptions`() {
        // Given
        val id = UUID.randomUUID()
        val originalDomain = TopicTag(
            id = id,
            tagName = "Mathematics",
            description = "Study of numbers, quantities, shapes & patterns (algebra, geometry, etc.)",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(originalDomain)
        val resultDomain = mapper.toDomainEntity(jpaEntity)

        // Then
        assertThat(resultDomain.description)
            .isEqualTo(originalDomain.description)
            .contains("&")
            .contains("(")
            .contains(")")
    }

    @Test
    fun `toJpaEntity should handle interdisciplinary topics`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = TopicTag(
            id = id,
            tagName = "Digital Humanities",
            description = "Intersection of computing and humanities disciplines for cultural analysis",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.topicTag).isEqualTo("Digital Humanities")
        assertThat(jpaEntity.description).contains("Intersection")
    }

    @Test
    fun `toDomainEntity should handle various cultural topics`() {
        // Given
        val culturalTopics = listOf(
            TopicTagJpaEntity().apply {
                id = UUID.randomUUID()
                topicTag = "Folklore"
                description = "Traditional beliefs, customs, and stories of a community"
                active = true
            },
            TopicTagJpaEntity().apply {
                id = UUID.randomUUID()
                topicTag = "Performing Arts"
                description = "Theater, dance, music, and other live performance arts"
                active = true
            }
        )

        // When
        val domainEntities = culturalTopics.map { mapper.toDomainEntity(it) }

        // Then
        assertThat(domainEntities).hasSize(2)
        assertThat(domainEntities[0].tagName).isEqualTo("Folklore")
        assertThat(domainEntities[1].tagName).isEqualTo("Performing Arts")
        assertThat(domainEntities).allMatch { it.active }
    }
}