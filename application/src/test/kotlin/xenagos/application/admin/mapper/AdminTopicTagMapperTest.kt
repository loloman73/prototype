package xenagos.application.admin.mapper

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import xenagos.application.port.input.admin.model.AdminTopicTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminTopicTagResponseDTO
import xenagos.application.port.input.admin.model.AdminTopicTagUpdateRequestDTO
import xenagos.domain.model.TopicTag
import java.util.*

class AdminTopicTagMapperTest {

    private val mapper = AdminTopicTagMapper()

    @Test
    fun `toResponseDto maps all fields`() {
        val id = UUID.randomUUID()
        val entity = TopicTag(id, "Architecture", "Buildings and structures", true)

        val dto: AdminTopicTagResponseDTO = mapper.toResponseDto(entity)

        assertThat(dto.id).isEqualTo(id)
        assertThat(dto.name).isEqualTo("Architecture")
        assertThat(dto.description).isEqualTo("Buildings and structures")
        assertThat(dto.active).isTrue()
    }

    @Test
    fun `toEntity from new request uses given id and maps fields`() {
        val id = UUID.randomUUID()
        val newDto = AdminTopicTagNewRequestDTO(
            name = "History",
            description = "Historical context",
            active = false
        )

        val entity: TopicTag = mapper.toEntity(newDto, id)

        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.tagName).isEqualTo("History")
        assertThat(entity.description).isEqualTo("Historical context")
        assertThat(entity.active).isFalse()
    }

    @Test
    fun `toEntity from update request maps all fields`() {
        val id = UUID.randomUUID()
        val updateDto = AdminTopicTagUpdateRequestDTO(
            id = id,
            name = "Art",
            description = "Fine arts and paintings",
            active = true
        )

        val entity: TopicTag = mapper.toEntity(updateDto)

        assertThat(entity.id).isEqualTo(id)
        assertThat(entity.tagName).isEqualTo("Art")
        assertThat(entity.description).isEqualTo("Fine arts and paintings")
        assertThat(entity.active).isTrue()
    }
}
