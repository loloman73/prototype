package xenagos.application.admin.mapper

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import xenagos.application.port.input.admin.model.AdminMediaTypeNewRequestDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeResponseDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeUpdateRequestDTO
import xenagos.domain.model.MediaType
import java.util.UUID

class AdminMediaTypeMapperTest {

    private val mapper = AdminMediaTypeMapper()

    @Test
    fun `toResponseDto maps all fields`() {
        val id = UUID.randomUUID()
        val entity = MediaType(id, "Audio", true)

        val dto: AdminMediaTypeResponseDTO = mapper.toResponseDto(entity)

        assertThat(dto.id).isEqualTo(id)
        assertThat(dto.name).isEqualTo("Audio")
        assertThat(dto.active).isTrue()
    }

    @Test
    fun `toEntity from new request uses given id and maps fields`() {
        val id = UUID.randomUUID()
        val newDto = AdminMediaTypeNewRequestDTO(name = "Video", active = false)

        val entity: MediaType = mapper.toEntity(newDto, id)

        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.type).isEqualTo("Video")
        assertThat(entity.active).isFalse()
    }

    @Test
    fun `toEntity from update request maps all fields`() {
        val id = UUID.randomUUID()
        val updateDto = AdminMediaTypeUpdateRequestDTO(id = id, name = "Text", active = true)

        val entity: MediaType = mapper.toEntity(updateDto)

        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.type).isEqualTo("Text")
        assertThat(entity.active).isTrue()
    }
}
