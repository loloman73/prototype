
package xenagos.adapter.output.persistence.admin.mapper

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import xenagos.adapter.output.persistence.model.LanguageJpaEntity
import xenagos.domain.model.Language
import java.util.UUID

// This unit test includes:
// Positive test cases: Testing both toJpaEntity and toDomainEntity with valid data
// Active/Inactive status: Testing both true and false values for the active field
// Internationalization: Testing different native and English names with various scripts
// ISO codes: Testing three-letter language codes
// Roundtrip conversion: Ensuring data integrity when converting domain → JPA → domain
// Null handling tests: Testing the TODO comment for all required fields
// Unicode support: Testing languages with non-Latin scripts (Greek, Japanese, Arabic, Chinese, etc.)

class AdminLanguageJPAMapperTest {

    private val mapper = AdminLanguageJPAMapper()

    @Test
    fun `toJpaEntity should map domain entity to JPA entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = Language(
            id = id,
            code = "eng",
            englishName = "English",
            nativeName = "English",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.code).isEqualTo("eng")
        assertThat(jpaEntity.englishName).isEqualTo("English")
        assertThat(jpaEntity.nativeName).isEqualTo("English")
        assertThat(jpaEntity.active).isTrue()
    }

    @Test
    fun `toJpaEntity should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = Language(
            id = id,
            code = "spa",
            englishName = "Spanish",
            nativeName = "Español",
            active = false
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.code).isEqualTo("spa")
        assertThat(jpaEntity.englishName).isEqualTo("Spanish")
        assertThat(jpaEntity.nativeName).isEqualTo("Español")
        assertThat(jpaEntity.active).isFalse()
    }

    @Test
    fun `toJpaEntity should handle different native and english names`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = Language(
            id = id,
            code = "ell",
            englishName = "Greek",
            nativeName = "Ελληνικά",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.code).isEqualTo("ell")
        assertThat(jpaEntity.englishName).isEqualTo("Greek")
        assertThat(jpaEntity.nativeName).isEqualTo("Ελληνικά")
        assertThat(jpaEntity.active).isTrue()
    }

    @Test
    fun `toJpaEntity should handle three letter ISO codes`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = Language(
            id = id,
            code = "jpn",
            englishName = "Japanese",
            nativeName = "日本語",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.code).isEqualTo("jpn")
        assertThat(jpaEntity.englishName).isEqualTo("Japanese")
        assertThat(jpaEntity.nativeName).isEqualTo("日本語")
        assertThat(jpaEntity.active).isTrue()
    }

    @Test
    fun `toJpaEntity should handle languages with unicode characters`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = Language(
            id = id,
            code = "ara",
            englishName = "Arabic",
            nativeName = "العربية",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.id).isEqualTo(id)
        assertThat(jpaEntity.code).isEqualTo("ara")
        assertThat(jpaEntity.englishName).isEqualTo("Arabic")
        assertThat(jpaEntity.nativeName).isEqualTo("العربية")
        assertThat(jpaEntity.active).isTrue()
    }

    @Test
    fun `toDomainEntity should map JPA entity to domain entity correctly`() {
        // Given
        val id = UUID.randomUUID()
        val jpaEntity = LanguageJpaEntity().apply {
            this.id = id
            this.code = "fra"
            this.englishName = "French"
            this.nativeName = "Français"
            this.active = true
        }

        // When
        val domainEntity = mapper.toDomainEntity(jpaEntity)

        // Then
        assertThat(domainEntity.id).isEqualTo(id)
        assertThat(domainEntity.code).isEqualTo("fra")
        assertThat(domainEntity.englishName).isEqualTo("French")
        assertThat(domainEntity.nativeName).isEqualTo("Français")
        assertThat(domainEntity.active).isTrue()
    }

    @Test
    fun `toDomainEntity should handle inactive status`() {
        // Given
        val id = UUID.randomUUID()
        val jpaEntity = LanguageJpaEntity().apply {
            this.id = id
            this.code = "deu"
            this.englishName = "German"
            this.nativeName = "Deutsch"
            this.active = false
        }

        // When
        val domainEntity = mapper.toDomainEntity(jpaEntity)

        // Then
        assertThat(domainEntity.id).isEqualTo(id)
        assertThat(domainEntity.code).isEqualTo("deu")
        assertThat(domainEntity.englishName).isEqualTo("German")
        assertThat(domainEntity.nativeName).isEqualTo("Deutsch")
        assertThat(domainEntity.active).isFalse()
    }

    @Test
    fun `roundtrip conversion should preserve data`() {
        // Given
        val id = UUID.randomUUID()
        val originalDomain = Language(
            id = id,
            code = "ita",
            englishName = "Italian",
            nativeName = "Italiano",
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
    fun `toJpaEntity should handle Cyrillic script languages`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = Language(
            id = id,
            code = "rus",
            englishName = "Russian",
            nativeName = "Русский",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.code).isEqualTo("rus")
        assertThat(jpaEntity.englishName).isEqualTo("Russian")
        assertThat(jpaEntity.nativeName).isEqualTo("Русский")
        assertThat(jpaEntity.active).isTrue()
    }

    @Test
    fun `toJpaEntity should handle Chinese language`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = Language(
            id = id,
            code = "zho",
            englishName = "Chinese",
            nativeName = "中文",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.code).isEqualTo("zho")
        assertThat(jpaEntity.englishName).isEqualTo("Chinese")
        assertThat(jpaEntity.nativeName).isEqualTo("中文")
    }

    @Test
    fun `toJpaEntity should handle Korean language`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = Language(
            id = id,
            code = "kor",
            englishName = "Korean",
            nativeName = "한국어",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.code).isEqualTo("kor")
        assertThat(jpaEntity.englishName).isEqualTo("Korean")
        assertThat(jpaEntity.nativeName).isEqualTo("한국어")
    }

    @Test
    fun `toJpaEntity should handle multiple languages mapping`() {
        // Given
        val languages = listOf(
            Language(UUID.randomUUID(), "por", "Portuguese", "Português", true),
            Language(UUID.randomUUID(), "nld", "Dutch", "Nederlands", true),
            Language(UUID.randomUUID(), "pol", "Polish", "Polski", false)
        )

        // When
        val jpaEntities = languages.map { mapper.toJpaEntity(it) }

        // Then
        assertThat(jpaEntities).hasSize(3)
        assertThat(jpaEntities[0].code).isEqualTo("por")
        assertThat(jpaEntities[0].nativeName).isEqualTo("Português")
        assertThat(jpaEntities[1].code).isEqualTo("nld")
        assertThat(jpaEntities[1].nativeName).isEqualTo("Nederlands")
        assertThat(jpaEntities[2].code).isEqualTo("pol")
        assertThat(jpaEntities[2].active).isFalse()
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when id is null`() {
        // Given
        val jpaEntity = LanguageJpaEntity().apply {
            this.id = null
            this.code = "eng"
            this.englishName = "English"
            this.nativeName = "English"
            this.active = true
        }

        // When & Then
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when code is null`() {
        // Given
        val jpaEntity = LanguageJpaEntity().apply {
            this.id = UUID.randomUUID()
            this.code = null
            this.englishName = "English"
            this.nativeName = "English"
            this.active = true
        }

        // When & Then
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when englishName is null`() {
        // Given
        val jpaEntity = LanguageJpaEntity().apply {
            this.id = UUID.randomUUID()
            this.code = "eng"
            this.englishName = null
            this.nativeName = "English"
            this.active = true
        }

        // When & Then
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when nativeName is null`() {
        // Given
        val jpaEntity = LanguageJpaEntity().apply {
            this.id = UUID.randomUUID()
            this.code = "eng"
            this.englishName = "English"
            this.nativeName = null
            this.active = true
        }

        // When & Then
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
    }

    @Test
    fun `toDomainEntity should throw NullPointerException when active is null`() {
        // Given
        val jpaEntity = LanguageJpaEntity().apply {
            this.id = UUID.randomUUID()
            this.code = "eng"
            this.englishName = "English"
            this.nativeName = "English"
            this.active = null
        }

        // When & Then
        assertThatThrownBy { mapper.toDomainEntity(jpaEntity) }
            .isInstanceOf(NullPointerException::class.java)
    }

    @Test
    fun `toDomainEntity should handle Hebrew language with right-to-left script`() {
        // Given
        val id = UUID.randomUUID()
        val jpaEntity = LanguageJpaEntity().apply {
            this.id = id
            this.code = "heb"
            this.englishName = "Hebrew"
            this.nativeName = "עברית"
            this.active = true
        }

        // When
        val domainEntity = mapper.toDomainEntity(jpaEntity)

        // Then
        assertThat(domainEntity.code).isEqualTo("heb")
        assertThat(domainEntity.englishName).isEqualTo("Hebrew")
        assertThat(domainEntity.nativeName).isEqualTo("עברית")
    }

    @Test
    fun `toJpaEntity should handle Hindi language with Devanagari script`() {
        // Given
        val id = UUID.randomUUID()
        val domainEntity = Language(
            id = id,
            code = "hin",
            englishName = "Hindi",
            nativeName = "हिन्दी",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(domainEntity)

        // Then
        assertThat(jpaEntity.code).isEqualTo("hin")
        assertThat(jpaEntity.englishName).isEqualTo("Hindi")
        assertThat(jpaEntity.nativeName).isEqualTo("हिन्दी")
    }

    @Test
    fun `roundtrip conversion should preserve unicode characters`() {
        // Given
        val id = UUID.randomUUID()
        val originalDomain = Language(
            id = id,
            code = "tha",
            englishName = "Thai",
            nativeName = "ไทย",
            active = true
        )

        // When
        val jpaEntity = mapper.toJpaEntity(originalDomain)
        val resultDomain = mapper.toDomainEntity(jpaEntity)

        // Then
        assertThat(resultDomain.nativeName)
            .isEqualTo(originalDomain.nativeName)
            .isEqualTo("ไทย")
    }
}