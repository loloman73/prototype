package xenagos.adapter.input.web.admin

import org.assertj.core.api.Assertions.assertThat
import org.hibernate.validator.internal.constraintvalidators.bv.AssertTrueValidator
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import xenagos.PrototypeApplication
import xenagos.adapter.input.web.BaseWebIT
import xenagos.application.port.input.admin.AdminAccessibilityTagsUseCase
import xenagos.application.port.input.admin.model.AdminAccessibilityTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAccessibilityTagResponseDTO
import xenagos.application.port.input.admin.model.AdminAccessibilityTagUpdateRequestDTO

@SpringBootTest(classes = [PrototypeApplication::class])
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class AdminAccessibilityTagsControllerIT : BaseWebIT() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var useCase: AdminAccessibilityTagsUseCase

    private fun create(name: String, description: String, active: Boolean) =
        useCase.saveOneNew(
            AdminAccessibilityTagNewRequestDTO(
                name = name,
                description = description,
                active = active
            )
        )

    @Test
    @Order(1)
    fun `GET -With one existing entry, returns it in listAllModel -Returns empty DTOs for addOneNewModel and updateOneModel`() {

        // Arrange: add 1 valid entry using the UseCase
        val created = create(
            name = "Wheelchair Accessible",
            description = "Suitable for wheelchair users",
            active = true
        )

        // Act: call the GET endpoint
        val mvcResult = mockMvc.perform(get("/admin/accessibilityTags"))
            .andExpect(status().isOk)
            .andExpect(model().attributeExists("listAllModel", "addOneNewModel", "updateOneModel"))
            .andReturn()

        // Assert listAllModel contains the created entry with identical parameters
        @Suppress("UNCHECKED_CAST")
        val listAllModel = mvcResult.modelAndView!!.model["listAllModel"] as List<AdminAccessibilityTagResponseDTO>
        val found = listAllModel.firstOrNull { it.id == created.id }
        assertThat(found).withFailMessage("Expected created entry to appear in listAllModel").isNotNull
        assertThat(found!!.name).isEqualTo(created.name)
        assertThat(found.description).isEqualTo(created.description)
        assertThat(found.active).isEqualTo(created.active)

        // Assert addOneNewModel is an empty DTO
        val addOneNew = mvcResult.modelAndView!!.model["addOneNewModel"] as AdminAccessibilityTagNewRequestDTO
        assertThat(addOneNew.name).isEmpty()
        assertThat(addOneNew.description).isEmpty()
        assertThat(addOneNew.active).isFalse()

        // Assert updateOneModel is an empty DTO
        val updateOne = mvcResult.modelAndView!!.model["updateOneModel"] as AdminAccessibilityTagUpdateRequestDTO
        assertThat(updateOne.id).isNotNull
        assertThat(updateOne.name).isEmpty()
        assertThat(updateOne.description).isEmpty()
        assertThat(updateOne.active).isFalse()
    }

    @Test
    @Order(2)
    fun `GET -After adding two more entries, returns them with identical parameters in listAllModel`() {

        // Arrange: add 2 more valid entries using the UseCase
        val created2 = create(
            name = "Braille Signage",
            description = "Includes braille on signs",
            active = true
        )
        val created3 = create(
            name = "Assistive Listening",
            description = "Supports assistive listening devices",
            active = false
        )

        // Act: call the GET endpoint
        val mvcResult = mockMvc.perform(get("/admin/accessibilityTags"))
            .andExpect(status().isOk)
            .andExpect(model().attributeExists("listAllModel"))
            .andReturn()

        // Assert listAllModel contains both newly created entries with identical fields
        @Suppress("UNCHECKED_CAST")
        val listAllModel = mvcResult.modelAndView!!.model["listAllModel"] as List<AdminAccessibilityTagResponseDTO>

        val second = listAllModel.firstOrNull { it.id == created2.id }
        assertThat(second).withFailMessage("Expected second entry to appear in listAllModel").isNotNull
        assertThat(second!!.name).isEqualTo(created2.name)
        assertThat(second.description).isEqualTo(created2.description)
        assertThat(second.active).isEqualTo(created2.active)

        val third = listAllModel.firstOrNull { it.id == created3.id }
        assertThat(third).withFailMessage("Expected third entry to appear in listAllModel").isNotNull
        assertThat(third!!.name).isEqualTo(created3.name)
        assertThat(third.description).isEqualTo(created3.description)
        assertThat(third.active).isEqualTo(created3.active)
    }

    @Test
    @Order(3)
    fun `POST -Add one valid entry via HTMX, responds with HX-Redirect and create identical entry`() {
        // Act: call the POST endpoint, using HTMX, with one valid entry
        // Assert status and Header
        val mvcResult = mockMvc.perform(
            post("/admin/accessibilityTags/addNew")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("name", "Test Post Request")
                .param("description", "Test Post Request description")
                .param("active", "true")
        )
            .andExpect(status().isOk)
            .andExpect(header().string("HX-Redirect", "/admin/accessibilityTags"))

        //Assert entry created
        val created = useCase.getAll().firstOrNull { it.name == "Test Post Request" }
        assertThat(created).withFailMessage("Expected created entry to appear in listAllModel").isNotNull
    }

    @Test
    @Order(4)
    fun `POST -Add one entry with invalid 'name' param, via HTMX, responds with HX-Redirect and does not create an entry`() {
        val mvcResult = mockMvc.perform(
            post("/admin/accessibilityTags/addNew")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("name", "")
                .param("description", "This is a valid description")
                .param("active", "true")
        )
    }

}