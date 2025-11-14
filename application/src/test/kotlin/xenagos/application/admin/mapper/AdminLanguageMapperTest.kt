package xenagos.application.admin.mapper

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import xenagos.application.port.input.admin.model.AdminLanguageNewRequestDTO
import xenagos.application.port.input.admin.model.AdminLanguageResponseDTO
import xenagos.application.port.input.admin.model.AdminLanguageUpdateRequestDTO
import xenagos.domain.model.Language
import java.util.*

class AdminLanguageMapperTest {

    private val mapper = AdminLanguageMapper()

    @Test
    fun `toResponseDto maps all fields`() {
        val id = UUID.randomUUID()
        val entity = Language(id, code = "eng", englishName = "English", nativeName = "English", active = true)

        val dto: AdminLanguageResponseDTO = mapper.toResponseDto(entity)

        assertThat(dto.id).isEqualTo(id)
        assertThat(dto.code).isEqualTo("eng")
        assertThat(dto.englishName).isEqualTo("English")
        assertThat(dto.nativeName).isEqualTo("English")
        assertThat(dto.active).isTrue()
    }

    @Test
    fun `toEntity from new request uses given id and maps fields`() {
        val id = UUID.randomUUID()
        val newDto = AdminLanguageNewRequestDTO(
            code = "ell",
            englishName = "Greek",
            nativeName = "Ελληνικά",
            active = false
        )

        val entity: Language = mapper.toEntity(newDto, id)

        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.code).isEqualTo("ell")
        assertThat(entity.englishName).isEqualTo("Greek")
        assertThat(entity.nativeName).isEqualTo("Ελληνικά")
        assertThat(entity.active).isFalse()
    }

    @Test
    fun `toEntity from update request maps all fields`() {
        val id = UUID.randomUUID()
        val updateDto = AdminLanguageUpdateRequestDTO(
            id = id,
            code = "spa",
            englishName = "Spanish",
            nativeName = "Español",
            active = true
        )

        val entity: Language = mapper.toEntity(updateDto)

        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.code).isEqualTo("spa")
        assertThat(entity.englishName).isEqualTo("Spanish")
        assertThat(entity.nativeName).isEqualTo("Español")
        assertThat(entity.active).isTrue()
    }
}
