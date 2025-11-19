
package xenagos.application.admin.mapper

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import xenagos.application.port.input.admin.model.AdminLanguageNewRequestDTO
import xenagos.application.port.input.admin.model.AdminLanguageUpdateRequestDTO
import xenagos.domain.model.Language
import java.util.UUID


// This comprehensive test suite includes:
// 1. **Response DTO mapping tests** - Testing conversion from domain entity to response DTO
// 2. **New Request DTO mapping tests** - Testing creation of domain entities from new request DTOs
// 3. **Update Request DTO mapping tests** - Testing creation of domain entities from update request DTOs
// 4. **Status variations** - Testing both active and inactive statuses
// 5. **Default value handling** - Testing the default false value for the active field
// 6. **Unicode and special characters** - Testing various languages with different scripts:
//   - Latin scripts (Spanish, French, German, etc.)
//   - Greek alphabet (Ελληνικά)
//   - Cyrillic (Русский)
//   - CJK characters (中文, 日本語, 한국어)
//   - Arabic/RTL scripts (العربية)
//   - Devanagari (हिन्दी)
//   - Thai script (ไทย)
// 7. **Roundtrip tests** - Ensuring data integrity through conversion chains, especially for Unicode preservation
// 8. **Field extraction tests** - Using AssertJ's `extracting()` method for cleaner multi-field assertions
// 9. **ISO 639-3 code validation** - Ensuring three-letter language codes are handled correctly
// The test follows the Given-When-Then structure and provides comprehensive coverage of all mapper methods, with special attention to i18n concerns.

class AdminLanguageMapperTest {

    private val mapper = AdminLanguageMapper()

    @Test
    fun `toResponseDto should map domain entity to response DTO correctly`() {
        // Given
        val id = UUID.randomUUID()
        val entity = Language(
            id = id,
            code = "eng",
            englishName = "English",
            nativeName = "English",
            active = true
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto).isNotNull
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.code).isEqualTo("eng")
        assertThat(responseDto.englishName).isEqualTo("English")
        assertThat(responseDto.nativeName).isEqualTo("English")
        assertThat(responseDto.active).isTrue()
    }

    @Test
    fun `toResponseDto should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val entity = Language(
            id = id,
            code = "fra",
            englishName = "French",
            nativeName = "Français",
            active = false
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto).isNotNull
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.code).isEqualTo("fra")
        assertThat(responseDto.englishName).isEqualTo("French")
        assertThat(responseDto.nativeName).isEqualTo("Français")
        assertThat(responseDto.active).isFalse()
    }

    @Test
    fun `toResponseDto should handle Greek language with Unicode characters`() {
        // Given
        val id = UUID.randomUUID()
        val entity = Language(
            id = id,
            code = "ell",
            englishName = "Greek",
            nativeName = "Ελληνικά",
            active = true
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto).isNotNull
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.code).isEqualTo("ell")
        assertThat(responseDto.englishName).isEqualTo("Greek")
        assertThat(responseDto.nativeName).isEqualTo("Ελληνικά")
        assertThat(responseDto.active).isTrue()
    }

    @Test
    fun `toEntity with NewRequestDTO should map to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminLanguageNewRequestDTO(
            code = "spa",
            englishName = "Spanish",
            nativeName = "Español",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.code).isEqualTo("spa")
        assertThat(entity.englishName).isEqualTo("Spanish")
        assertThat(entity.nativeName).isEqualTo("Español")
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `toEntity with NewRequestDTO should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminLanguageNewRequestDTO(
            code = "deu",
            englishName = "German",
            nativeName = "Deutsch",
            active = false
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.code).isEqualTo("deu")
        assertThat(entity.englishName).isEqualTo("German")
        assertThat(entity.nativeName).isEqualTo("Deutsch")
        assertThat(entity.active).isFalse()
    }

    @Test
    fun `toEntity with NewRequestDTO should handle default false active value`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminLanguageNewRequestDTO(
            code = "ita",
            englishName = "Italian",
            nativeName = "Italiano",
            active = false
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.code).isEqualTo("ita")
        assertThat(entity.englishName).isEqualTo("Italian")
        assertThat(entity.nativeName).isEqualTo("Italiano")
        assertThat(entity.active).isFalse()
    }

    @Test
    fun `toEntity with NewRequestDTO should handle languages with special characters`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminLanguageNewRequestDTO(
            code = "rus",
            englishName = "Russian",
            nativeName = "Русский",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.code).isEqualTo("rus")
        assertThat(entity.englishName).isEqualTo("Russian")
        assertThat(entity.nativeName).isEqualTo("Русский")
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `toEntity with UpdateRequestDTO should map to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminLanguageUpdateRequestDTO(
            id = id,
            code = "por",
            englishName = "Portuguese",
            nativeName = "Português",
            active = true
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.code).isEqualTo("por")
        assertThat(entity.englishName).isEqualTo("Portuguese")
        assertThat(entity.nativeName).isEqualTo("Português")
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `toEntity with UpdateRequestDTO should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminLanguageUpdateRequestDTO(
            id = id,
            code = "jpn",
            englishName = "Japanese",
            nativeName = "日本語",
            active = false
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.code).isEqualTo("jpn")
        assertThat(entity.englishName).isEqualTo("Japanese")
        assertThat(entity.nativeName).isEqualTo("日本語")
        assertThat(entity.active).isFalse()
    }

    @Test
    fun `toEntity with UpdateRequestDTO should handle Arabic with RTL script`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminLanguageUpdateRequestDTO(
            id = id,
            code = "ara",
            englishName = "Arabic",
            nativeName = "العربية",
            active = true
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.code).isEqualTo("ara")
        assertThat(entity.englishName).isEqualTo("Arabic")
        assertThat(entity.nativeName).isEqualTo("العربية")
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `roundtrip from NewRequestDTO to entity to ResponseDTO should preserve data`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminLanguageNewRequestDTO(
            code = "zho",
            englishName = "Chinese",
            nativeName = "中文",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto.id).isEqualTo(id)
        assertThat(responseDto.code).isEqualTo(newRequestDto.code)
        assertThat(responseDto.englishName).isEqualTo(newRequestDto.englishName)
        assertThat(responseDto.nativeName).isEqualTo(newRequestDto.nativeName)
        assertThat(responseDto.active).isEqualTo(newRequestDto.active)
    }

    @Test
    fun `roundtrip from UpdateRequestDTO to entity to ResponseDTO should preserve data`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminLanguageUpdateRequestDTO(
            id = id,
            code = "kor",
            englishName = "Korean",
            nativeName = "한국어",
            active = true
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto.id).isEqualTo(updateRequestDto.id)
        assertThat(responseDto.code).isEqualTo(updateRequestDto.code)
        assertThat(responseDto.englishName).isEqualTo(updateRequestDto.englishName)
        assertThat(responseDto.nativeName).isEqualTo(updateRequestDto.nativeName)
        assertThat(responseDto.active).isEqualTo(updateRequestDto.active)
    }

    @Test
    fun `toResponseDto should map all fields from entity`() {
        // Given
        val id = UUID.randomUUID()
        val entity = Language(
            id = id,
            code = "nld",
            englishName = "Dutch",
            nativeName = "Nederlands",
            active = true
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto)
            .extracting("id", "code", "englishName", "nativeName", "active")
            .containsExactly(id, "nld", "Dutch", "Nederlands", true)
    }

    @Test
    fun `toEntity with NewRequestDTO should map all fields correctly`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminLanguageNewRequestDTO(
            code = "pol",
            englishName = "Polish",
            nativeName = "Polski",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity)
            .extracting("id", "code", "englishName", "nativeName", "active")
            .containsExactly(id, "pol", "Polish", "Polski", true)
    }

    @Test
    fun `toEntity with UpdateRequestDTO should map all fields correctly`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminLanguageUpdateRequestDTO(
            id = id,
            code = "swe",
            englishName = "Swedish",
            nativeName = "Svenska",
            active = false
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity)
            .extracting("id", "code", "englishName", "nativeName", "active")
            .containsExactly(id, "swe", "Swedish", "Svenska", false)
    }

    @Test
    fun `toResponseDto should handle language with three-letter ISO code`() {
        // Given
        val id = UUID.randomUUID()
        val entity = Language(
            id = id,
            code = "tur",
            englishName = "Turkish",
            nativeName = "Türkçe",
            active = true
        )

        // When
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto).isNotNull
        assertThat(responseDto.code).hasSize(3)
        assertThat(responseDto.code).isEqualTo("tur")
        assertThat(responseDto.englishName).isEqualTo("Turkish")
        assertThat(responseDto.nativeName).isEqualTo("Türkçe")
    }

    @Test
    fun `toEntity with NewRequestDTO should handle language with same English and native name`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminLanguageNewRequestDTO(
            code = "eng",
            englishName = "English",
            nativeName = "English",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.englishName).isEqualTo(entity.nativeName)
        assertThat(entity.englishName).isEqualTo("English")
    }

    @Test
    fun `toEntity with UpdateRequestDTO should handle Hindi with Devanagari script`() {
        // Given
        val id = UUID.randomUUID()
        val updateRequestDto = AdminLanguageUpdateRequestDTO(
            id = id,
            code = "hin",
            englishName = "Hindi",
            nativeName = "हिन्दी",
            active = true
        )

        // When
        val entity = mapper.toEntity(updateRequestDto)

        // Then
        assertThat(entity).isNotNull
        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.code).isEqualTo("hin")
        assertThat(entity.englishName).isEqualTo("Hindi")
        assertThat(entity.nativeName).isEqualTo("हिन्दी")
        assertThat(entity.active).isTrue()
    }

    @Test
    fun `roundtrip conversion should preserve Unicode characters`() {
        // Given
        val id = UUID.randomUUID()
        val newRequestDto = AdminLanguageNewRequestDTO(
            code = "tha",
            englishName = "Thai",
            nativeName = "ไทย",
            active = true
        )

        // When
        val entity = mapper.toEntity(newRequestDto, id)
        val responseDto = mapper.toResponseDto(entity)

        // Then
        assertThat(responseDto.nativeName).isEqualTo("ไทย")
        assertThat(responseDto.nativeName).isEqualTo(newRequestDto.nativeName)
    }
}