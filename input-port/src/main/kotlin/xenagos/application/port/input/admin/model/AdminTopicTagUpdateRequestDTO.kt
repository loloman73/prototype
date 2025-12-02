package xenagos.application.port.input.admin.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.*

data class AdminTopicTagUpdateRequestDTO(

    @field:NotNull
    val id: UUID,

    @field:NotBlank
    @field:Size(min = 3, max = 35)
    val entityName: String,

    @field:NotBlank
    @field:Size(min = 3, max = 250)
    val description: String,

    @field:NotNull
    val active: Boolean = false

) : BaseAdminUpdateRequestDTO{
    companion object {
        fun createEmptyDeactivated() = AdminTopicTagUpdateRequestDTO(UUID.randomUUID(), "", "")
    }
}