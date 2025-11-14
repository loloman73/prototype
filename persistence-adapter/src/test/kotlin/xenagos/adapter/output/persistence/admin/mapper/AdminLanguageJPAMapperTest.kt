package xenagos.adapter.output.persistence.admin.mapper

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import xenagos.adapter.output.persistence.model.LanguageJpaEntity
import xenagos.domain.model.Language
import java.util.UUID

// The test suite includes:
// 1. Basic mapping tests for both directions
// 2. Active/inactive status tests
// 3. Different native and English names (important for internationalization)
// 4. Roundtrip conversion test for data integrity
// 5. ISO code handling tests (3-letter codes)
// 6. Unicode character tests (for languages with non-Latin scripts)
// 7. Null handling tests for all required fields (addressing the TODO comment)

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
        assertEquals(id, jpaEntity.id)
        assertEquals("eng", jpaEntity.code)
        assertEquals("English", jpaEntity.englishName)
        assertEquals("English", jpaEntity.nativeName)
        assertTrue(jpaEntity.active!!)
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
        assertEquals(id, jpaEntity.id)
        assertEquals("spa", jpaEntity.code)
        assertEquals("Spanish", jpaEntity.englishName)
        assertEquals("Español", jpaEntity.nativeName)
        assertFalse(jpaEntity.active!!)
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
        assertEquals(id, jpaEntity.id)
        assertEquals("ell", jpaEntity.code)
        assertEquals("Greek", jpaEntity.englishName)
        assertEquals("Ελληνικά", jpaEntity.nativeName)
        assertTrue(jpaEntity.active!!)
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
        assertEquals(id, domainEntity.id)
        assertEquals("fra", domainEntity.code)
        assertEquals("French", domainEntity.englishName)
        assertEquals("Français", domainEntity.nativeName)
        assertTrue(domainEntity.active)
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
        assertEquals(id, domainEntity.id)
        assertEquals("deu", domainEntity.code)
        assertEquals("German", domainEntity.englishName)
        assertEquals("Deutsch", domainEntity.nativeName)
        assertFalse(domainEntity.active)
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
        assertEquals(originalDomain.id, resultDomain.id)
        assertEquals(originalDomain.code, resultDomain.code)
        assertEquals(originalDomain.englishName, resultDomain.englishName)
        assertEquals(originalDomain.nativeName, resultDomain.nativeName)
        assertEquals(originalDomain.active, resultDomain.active)
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
        assertEquals(id, jpaEntity.id)
        assertEquals("jpn", jpaEntity.code)
        assertEquals("Japanese", jpaEntity.englishName)
        assertEquals("日本語", jpaEntity.nativeName)
        assertTrue(jpaEntity.active!!)
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
        assertThrows(NullPointerException::class.java) {
            mapper.toDomainEntity(jpaEntity)
        }
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
        assertThrows(NullPointerException::class.java) {
            mapper.toDomainEntity(jpaEntity)
        }
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
        assertThrows(NullPointerException::class.java) {
            mapper.toDomainEntity(jpaEntity)
        }
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
        assertThrows(NullPointerException::class.java) {
            mapper.toDomainEntity(jpaEntity)
        }
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
        assertThrows(NullPointerException::class.java) {
            mapper.toDomainEntity(jpaEntity)
        }
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
        assertEquals(id, jpaEntity.id)
        assertEquals("ara", jpaEntity.code)
        assertEquals("Arabic", jpaEntity.englishName)
        assertEquals("العربية", jpaEntity.nativeName)
        assertTrue(jpaEntity.active!!)
    }
}