package xenagos.adapter.input.web.admin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
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
import xenagos.adapter.input.web.BaseWebIT
import xenagos.application.port.input.admin.AdminDurationTypesUseCase
import xenagos.application.port.input.admin.model.AdminDurationTypeNewRequestDTO
import xenagos.application.port.input.admin.model.AdminDurationTypeResponseDTO
import xenagos.application.port.input.admin.model.AdminDurationTypeUpdateRequestDTO

@SpringBootTest(classes = [PrototypeApplication::class])
@AutoConfigureMockMvc
class AdminDurationTypesControllerIT : BaseWebIT() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var useCase: AdminDurationTypesUseCase

    private val endPoint = "/admin/duration-types"

    private fun create(entityName: String, active: Boolean) =
        useCase.saveOneNew(
            AdminDurationTypeNewRequestDTO(
                entityName = entityName,
                active = active
            )
        )

    // GET tests
    @Test
    fun `GET -With one existing entry`() {
        // Arrange
        val created = create(entityName = "Short", active = true)

        // Act
        val mvcResult = mockMvc.perform(get(endPoint))
            .andExpect(status().isOk)
            .andExpect(model().attributeExists("listAllModel", "addOneNewModel", "updateOneModel"))
            .andReturn()

        // Assert: listAllModel contains the created entry
        @Suppress("UNCHECKED_CAST")
        val listAllModel = mvcResult.modelAndView!!.model["listAllModel"] as List<AdminDurationTypeResponseDTO>
        val found = listAllModel.firstOrNull { it.id == created.id }
        assertThat(found).withFailMessage("Expected created entry to appear in listAllModel").isNotNull
        assertThat(found!!.entityName).isEqualTo(created.entityName)
        assertThat(found.active).isEqualTo(created.active)

        // addOneNewModel defaults
        val addOneNew = mvcResult.modelAndView!!.model["addOneNewModel"] as AdminDurationTypeNewRequestDTO
        assertThat(addOneNew.entityName).isEmpty()
        assertThat(addOneNew.active).isFalse()

        // updateOneModel defaults
        val updateOne = mvcResult.modelAndView!!.model["updateOneModel"] as AdminDurationTypeUpdateRequestDTO
        assertThat(updateOne.id).isNotNull
        assertThat(updateOne.entityName).isEmpty()
        assertThat(updateOne.active).isFalse()
    }

    @Test
    fun `GET -After adding two more entries`() {
        // Arrange
        val created2 = create(entityName = "Half Day", active = true)
        val created3 = create(entityName = "Full Day", active = false)

        // Act
        val mvcResult = mockMvc.perform(get(endPoint))
            .andExpect(status().isOk)
            .andExpect(model().attributeExists("listAllModel"))
            .andReturn()

        // Assert list includes both
        @Suppress("UNCHECKED_CAST")
        val listAllModel = mvcResult.modelAndView!!.model["listAllModel"] as List<AdminDurationTypeResponseDTO>

        val second = listAllModel.firstOrNull { it.id == created2.id }
        assertThat(second).isNotNull
        assertThat(second!!.entityName).isEqualTo(created2.entityName)
        assertThat(second.active).isEqualTo(created2.active)

        val third = listAllModel.firstOrNull { it.id == created3.id }
        assertThat(third).isNotNull
        assertThat(third!!.entityName).isEqualTo(created3.entityName)
        assertThat(third.active).isEqualTo(created3.active)
    }

    // POST tests
    @Test
    fun `POST -Add one valid entry with active=TRUE`() {
        val nameParam = "Duration Type A"
        val countBefore = useCase.getAll().count()

        mockMvc.perform(
            post(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("entityName", nameParam)
                .param("active", "true")
        )
            .andExpect(status().isCreated)
            .andExpect(header().string("HX-Redirect", endPoint))

        val countAfter = useCase.getAll().count()
        assertThat(countAfter).isEqualTo(countBefore + 1)

        val created = useCase.getAll().firstOrNull { it.entityName == nameParam }
        assertThat(created).isNotNull
    }

    @Test
    fun `POST -Add valid entry with active=FALSE (missing)`() {
        val nameParam = "Duration Type B"
        val countBefore = useCase.getAll().count()

        mockMvc.perform(
            post(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("entityName", nameParam)
        )
            .andExpect(status().isCreated)
            .andExpect(header().string("HX-Redirect", endPoint))

        val created = useCase.getAll().first { it.entityName == nameParam }
        assertThat(created.active).isFalse()
        val countAfter = useCase.getAll().count()
        assertThat(countAfter).isEqualTo(countBefore + 1)
    }

    @Test
    fun `POST -Add entry with invalid 'name' param`() {
        val countBefore = useCase.getAll().count()

        val mvcResult = mockMvc.perform(
            post(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("entityName", "")
                .param("active", "true")
        )
            .andExpect(status().isUnprocessableEntity)
            .andReturn()

        assertThat(mvcResult.response.contentAsString).containsOnlyOnce("is-invalid")
        assertThat(useCase.getAll().count()).isEqualTo(countBefore)
    }

    // PUT tests
    @Test
    fun `PUT -all new params valid, dont change active=TRUE`() {
        val created = create(entityName = "Base Name", active = true)

        mockMvc.perform(
            put(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("entityName", "Updated Name")
                .param("active", created.active.toString())
        )
            .andExpect(status().isOk)
            .andExpect(header().string("HX-Redirect", endPoint))

        val after = useCase.getAll().first { it.id == created.id }
        assertThat(after.entityName).isEqualTo("Updated Name")
        assertThat(after.active).isTrue()
    }

    @Test
    fun `PUT -only change active from TRUE to FALSE (missing)`() {
        val created = create(entityName = "Keep Name", active = true)

        mockMvc.perform(
            put(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("entityName", created.entityName)
                // active omitted -> false
        )
            .andExpect(status().isOk)
            .andExpect(header().string("HX-Redirect", endPoint))

        val after = useCase.getAll().first { it.id == created.id }
        assertThat(after.entityName).isEqualTo(created.entityName)
        assertThat(after.active).isFalse()
    }

    @Test
    fun `PUT -change 'name' param with invalid`() {
        val created = create(entityName = "Valid Name", active = true)

        val mvcResult = mockMvc.perform(
            put(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("entityName", "")
                .param("active", created.active.toString())
        )
            .andExpect(status().isUnprocessableEntity)
            .andReturn()

        assertThat(mvcResult.response.contentAsString).containsOnlyOnce("is-invalid")

        val after = useCase.getAll().first { it.id == created.id }
        assertThat(after.entityName).isEqualTo(created.entityName)
        assertThat(after.active).isEqualTo(created.active)
    }

    // DELETE tests
    @Test
    fun `DELETE -valid entry`() {
        val created = create(entityName = "Delete Me", active = true)

        mockMvc.perform(
            delete(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
        )
            .andExpect(status().isNoContent)
            .andExpect(header().string("HX-Redirect", endPoint))

        assertThat(useCase.getAll().find { it.id == created.id }).isNull()
    }
}
