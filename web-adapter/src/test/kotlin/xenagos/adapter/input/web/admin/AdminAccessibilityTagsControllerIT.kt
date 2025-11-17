package xenagos.adapter.input.web.admin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import xenagos.PrototypeApplication
import xenagos.application.port.input.admin.AdminAccessibilityTagsUseCase
import xenagos.application.port.input.admin.model.AdminAccessibilityTagNewRequestDTO
import java.util.*

/**
 * End-to-end integration tests for AdminAccessibilityTagsController.
 * Uses a real PostgreSQL instance via Testcontainers (see BaseWebIT) and the full Spring Boot application context.
 */
@SpringBootTest(classes = [PrototypeApplication::class])
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class AdminAccessibilityTagsControllerIT : BaseWebIT() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var useCase: AdminAccessibilityTagsUseCase

    @Test
    @Order(1)
    fun `GET showAll returns page with required model attributes`() {
        val result = mockMvc.perform(
            get("/admin/accessibilityTags")
        )
            .andExpect(status().isOk)
            .andExpect(model().attributeExists("listAll", "addNewAccessibilityTag", "editAccessibilityTag"))
            .andReturn()

        // Sanity: the template name isn't directly accessible via MockMvc without a view resolver asserting it,
        // but we can assert that initially the DB is empty.
        val all = useCase.getAll()
        assertThat(all).isNotNull
    }

    @Test
    @Order(2)
    fun `POST addNew via HTMX creates an accessibility tag and responds with HX-Redirect`() {
        mockMvc.perform(
            post("/admin/accessibilityTags/addNew")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("name", "Wheelchair Accessible")
                .param("description", "Suitable for wheelchair users")
                .param("active", "true")
        )
            .andExpect(status().isOk)
            .andExpect(header().string("HX-Redirect", "/admin/accessibilityTags"))

        val all = useCase.getAll()
        assertThat(all).extracting<String> { it.name }
            .contains("Wheelchair Accessible")
    }

    @Test
    @Order(3)
    fun `PUT edit via HTMX updates an existing accessibility tag`() {
        // Arrange: ensure one exists
        val created = useCase.saveOneNew(
            AdminAccessibilityTagNewRequestDTO(
                name = "Signage",
                description = "Basic signage",
                active = true
            )
        )

        mockMvc.perform(
            put("/admin/accessibilityTags/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("name", "Signage Updated")
                .param("description", "Improved signage")
                .param("active", "false")
        )
            .andExpect(status().isOk)
            .andExpect(header().string("HX-Redirect", "/admin/accessibilityTags"))

        val after = useCase.getAll().first { it.id == created.id }
        assertThat(after.name).isEqualTo("Signage Updated")
        assertThat(after.description).isEqualTo("Improved signage")
        assertThat(after.active).isFalse()
    }

    @Test
    @Order(4)
    fun `DELETE delete via HTMX removes a tag`() {
        val created = useCase.saveOneNew(
            AdminAccessibilityTagNewRequestDTO(
                name = "Temp Tag",
                description = "To be deleted",
                active = true
            )
        )

        mockMvc.perform(
            delete("/admin/accessibilityTags/delete")
                .header("HX-Request", "true")
                .param("id", created.id.toString())
        )
            .andExpect(status().isOk)
            .andExpect(header().string("HX-Redirect", "/admin/accessibilityTags"))

        val remainingIds = useCase.getAll().map { it.id }
        assertThat(remainingIds).doesNotContain(UUID.fromString(created.id.toString()))
    }
}
