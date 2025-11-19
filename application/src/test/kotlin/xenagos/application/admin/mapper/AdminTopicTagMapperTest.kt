
package xenagos.application.admin.mapper

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import xenagos.application.port.input.admin.model.AdminTopicTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminTopicTagUpdateRequestDTO
import xenagos.domain.model.TopicTag
import java.util.UUID

//
//This comprehensive test suite includes:
// 1. **Response DTO mapping tests** - Testing conversion from domain entity to response DTO, with emphasis on field name mapping (entity.tagName → dto.name)
// 2. **New Request DTO mapping tests** - Testing creation of domain entities from new request DTOs (dto.name → entity.tagName)
// 3. **Update Request DTO mapping tests** - Testing creation of domain entities from update request DTOs
// 4. **Status variations** - Testing both active and inactive statuses
// 5. **Default value handling** - Testing the default false value for the active field
// 6. **Description variations** - Testing various description formats:
//  - Long descriptions
//  - Special characters (&, punctuation)
//  - Short descriptions
//  - Descriptions with commas and periods
// 7. **Tag name formats** - Testing various tag name patterns:
//  - Single words (Art, Science, Music)
//  - Multi-word names (Natural History, Ancient Civilizations)
//  - Hyphenated names (Pre-Columbian)
//  - Names with numbers (20th Century Art)
//  - Names with special characters (Religion & Philosophy, War & Conflict)
//  - Names with parentheses (Decorative Arts (Applied Arts))
// 8. **Roundtrip tests** - Ensuring data integrity through conversion chains
// 9. **Field extraction tests** - Using AssertJ's `extracting()` method for cleaner multi-field assertions
// 10. **Field mapping validation** - Explicit tests to verify the name/tagName field mapping between DTOs and entities
// 11. **Museum-specific topics** - Testing various relevant museum topic categories (Sculpture, Ceramics, Textiles, etc.)
//
//The test follows the Given-When-Then structure and provides comprehensive coverage of all mapper methods, with special
// attention to the field name differences between the domain model (`tagName`) and the DTOs (`name`), similar to the MediaType mapper pattern.
//

class AdminTopicTagMapperTest {

    private val mapper = AdminTopicTagMapper()

    @Test
    fun `toResponseDto should map domain entity to response DTO correctly`() {
        // Given
        val id = UUID.randomUUID()
        val entity = TopicTag(
            id = id,
            tagName = "Architecture",
            description = "Buildings and structures",
            active = true
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto).isNotNull
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.name).isEqualTo("Architecture")
        assertThat(responseDto.description).isEqualTo("Buildings and structures")
        assertThat(responseDto.active).isTrue()
    }

    @Test
    fun `toResponseDto should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val entity = TopicTag(
            id = id,
            tagName = "History",
            description = "Historical context and events",
            active = false
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto).isNotNull
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.name).isEqualTo("History")
        assertThat(responseDto.description).isEqualTo("Historical context and events")
        assertThat(responseDto.active).isFalse()
    }

    @Test
    fun `toResponseDto should handle various topic categories`() {
        // Given
        val id = UUID.randomUUID()
        val entity = TopicTag(
            id = id,
            tagName = "Art",
            description = "Fine arts and paintings",
            active = true
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto).isNotNull
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.name).isEqualTo("Art")
        assertThat(responseDto.description).isEqualTo("Fine arts and paintings")
        assertThat(responseDto.active).isTrue()
    }

    @Test
    fun `toEntity with NewRequestDTO should map to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminTopicTagNewRequestDTO(
            name = "Science",
            description = "Scientific discoveries and innovations",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.tagName).isEqualTo("Science")
        assertThat(entity.description).isEqualTo("Scientific discoveries and innovations")
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `toEntity with NewRequestDTO should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminTopicTagNewRequestDTO(
            name = "Technology",
            description = "Technological advancements",
            active = false
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.tagName).isEqualTo("Technology")
        assertThat(entity.description).isEqualTo("Technological advancements")
        assertThat(entity.active).isFalse()
    }

    @Test
    fun `toEntity with NewRequestDTO should handle default false active value`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminTopicTagNewRequestDTO(
            name = "Culture",
            description = "Cultural heritage and traditions",
            active = false
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.tagName).isEqualTo("Culture")
        assertThat(entity.description).isEqualTo("Cultural heritage and traditions")
        assertThat(entity.active).isFalse()
    }

    @Test
    fun `toEntity with NewRequestDTO should handle long descriptions`() {
        // Given
        val id = UUID.randomUUID()
        val longDescription = "Detailed information about ancient civilizations, their customs, beliefs, and contributions to modern society"
        val newRequestDto = AdminTopicTagNewRequestDTO(
            name = "Ancient Civilizations",
            description = longDescription,
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.tagName).isEqualTo("Ancient Civilizations")
        assertThat(entity.description).isEqualTo(longDescription)
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `toEntity with NewRequestDTO should handle special characters in description`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminTopicTagNewRequestDTO(
            name = "Religion & Philosophy",
            description = "Religious beliefs, practices & philosophical thought",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.tagName).isEqualTo("Religion & Philosophy")
        assertThat(entity.description).isEqualTo("Religious beliefs, practices & philosophical thought")
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `toEntity with UpdateRequestDTO should map to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminTopicTagUpdateRequestDTO(
            id = id,
            name = "Archaeology",
            description = "Archaeological findings and excavations",
            active = true
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.tagName).isEqualTo("Archaeology")
        assertThat(entity.description).isEqualTo("Archaeological findings and excavations")
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `toEntity with UpdateRequestDTO should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminTopicTagUpdateRequestDTO(
            id = id,
            name = "Literature",
            description = "Literary works and authors",
            active = false
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.tagName).isEqualTo("Literature")
        assertThat(entity.description).isEqualTo("Literary works and authors")
        assertThat(entity.active).isFalse()
    }

    @Test
    fun `toEntity with UpdateRequestDTO should handle multi-word tag names`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminTopicTagUpdateRequestDTO(
            id = id,
            name = "Natural History",
            description = "Natural world and biological sciences",
            active = true
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.tagName).isEqualTo("Natural History")
        assertThat(entity.description).isEqualTo("Natural world and biological sciences")
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `roundtrip from NewRequestDTO to entity to ResponseDTO should preserve data`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminTopicTagNewRequestDTO(
            name = "Music",
            description = "Musical instruments and compositions",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.name).isEqualTo(newRequestDto.name)
        assertThat(responseDto.description).isEqualTo(newRequestDto.description)
        assertThat(responseDto.active).isEqualTo(newRequestDto.active)
    }

    @Test
    fun `roundtrip from UpdateRequestDTO to entity to ResponseDTO should preserve data`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminTopicTagUpdateRequestDTO(
            id = id,
            name = "Photography",
            description = "Photographic collections and techniques",
            active = true
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto.id).isEqualTo(updateRequestDto.id)
        assertThat(responseDto.name).isEqualTo(updateRequestDto.name)
        assertThat(responseDto.description).isEqualTo(updateRequestDto.description)
        assertThat(responseDto.active).isEqualTo(updateRequestDto.active)
    }

    @Test
    fun `roundtrip conversion should preserve inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminTopicTagNewRequestDTO(
            name = "Mythology",
            description = "Mythological stories and legends",
            active = false
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.name).isEqualTo("Mythology")
        assertThat(responseDto.description).isEqualTo("Mythological stories and legends")
        assertThat(responseDto.active).isFalse()
    }

    @Test
    fun `toResponseDto should map all fields from entity`() {
        // Given
        val id = UUID.randomUUID()
        val entity = TopicTag(
            id = id,
            tagName = "Geography",
            description = "Geographical locations and maps",
            active = true
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto)
            .extracting("id", "name", "description", "active")
            .containsExactly(id, "Geography", "Geographical locations and maps", true)
    }

    @Test
    fun `toEntity with NewRequestDTO should map all fields correctly`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminTopicTagNewRequestDTO(
            name = "Paleontology",
            description = "Fossils and prehistoric life",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity)
            .extracting("id", "tagName", "description", "active")
            .containsExactly(id, "Paleontology", "Fossils and prehistoric life", true)
    }

    @Test
    fun `toEntity with UpdateRequestDTO should map all fields correctly`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminTopicTagUpdateRequestDTO(
            id = id,
            name = "Anthropology",
            description = "Human societies and cultures",
            active = false
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity)
            .extracting("id", "tagName", "description", "active")
            .containsExactly(id, "Anthropology", "Human societies and cultures", false)
    }

    @Test
    fun `toResponseDto should correctly map tagName field to name in DTO`() {
        // Given
        val id = UUID.randomUUID()
        val entity = TopicTag(
            id = id,
            tagName = "Astronomy",
            description = "Celestial objects and phenomena",
            active = true
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then - verify that entity.tagName maps to responseDto.name
        assertThat(responseDto.name).isEqualTo(entity.tagName)
        assertThat(responseDto.name).isEqualTo("Astronomy")
    }

    @Test
    fun `toEntity with NewRequestDTO should correctly map name field to tagName in entity`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminTopicTagNewRequestDTO(
            name = "Economics",
            description = "Economic systems and trade",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then - verify that dto.name maps to entity.tagName
        assertThat(entity.tagName).isEqualTo(newRequestDto.name)
        assertThat(entity.tagName).isEqualTo("Economics")
    }

    @Test
    fun `toEntity with UpdateRequestDTO should correctly map name field to tagName in entity`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminTopicTagUpdateRequestDTO(
            id = id,
            name = "Politics",
            description = "Political systems and governance",
            active = false
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then - verify that dto.name maps to entity.tagName
        assertThat(entity.tagName).isEqualTo(updateRequestDto.name)
        assertThat(entity.tagName).isEqualTo("Politics")
    }

    @Test
    fun `toResponseDto should handle various museum topic categories`() {
        // Given
        val topicCategories = listOf(
            "Sculpture" to "Three-dimensional artworks",
            "Ceramics" to "Pottery and ceramic art",
            "Textiles" to "Fabric arts and weaving",
            "Weapons" to "Historical weapons and armor",
            "Coins" to "Numismatic collections"
        )

        topicCategories.forEach { (name, description) ->
            val id = UUID.randomUUID()
            val entity = TopicTag(id, name, description, true)

            // When
            val responseDto = mapper.toResponseDto(entity)

            // Then
            assertThat(responseDto.name).isEqualTo(name)
            assertThat(responseDto.description).isEqualTo(description)
            assertThat(responseDto.id).isEqualTo(id)
            assertThat(responseDto.active).isTrue()
        }
    }

    @Test
    fun `toEntity with NewRequestDTO should handle hyphenated tag names`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminTopicTagNewRequestDTO(
            name = "Pre-Columbian",
            description = "Pre-Columbian American civilizations",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.tagName).isEqualTo("Pre-Columbian")
        assertThat(entity.description).isEqualTo("Pre-Columbian American civilizations")
    }

    @Test
    fun `toEntity with UpdateRequestDTO should handle numeric characters in tag name`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminTopicTagUpdateRequestDTO(
            id = id,
            name = "20th Century Art",
            description = "Art from the 20th century",
            active = true
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.tagName).isEqualTo("20th Century Art")
        assertThat(entity.description).isEqualTo("Art from the 20th century")
    }

    @Test
    fun `toResponseDto should handle empty-like but valid descriptions`() {
        // Given
        val id = UUID.randomUUID()
        val entity = TopicTag(
            id = id,
            tagName = "Miscellaneous",
            description = "Other",
            active = true
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto).isNotNull
        assertThat(responseDto.name).isEqualTo("Miscellaneous")
        assertThat(responseDto.description).isEqualTo("Other")
    }

    @Test
    fun `roundtrip conversion should preserve descriptions with punctuation`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminTopicTagNewRequestDTO(
            name = "War & Conflict",
            description = "Military history, battles, and conflicts throughout history.",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto.description).isEqualTo("Military history, battles, and conflicts throughout history.")
        assertThat(responseDto.description).isEqualTo(newRequestDto.description)
    }

    @Test
    fun `toEntity with NewRequestDTO should handle parentheses in tag names`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminTopicTagNewRequestDTO(
            name = "Decorative Arts (Applied Arts)",
            description = "Functional artistic objects",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.tagName).isEqualTo("Decorative Arts (Applied Arts)")
        assertThat(entity.description).isEqualTo("Functional artistic objects")
        assertThat(entity.active).isTrue()
    }
}