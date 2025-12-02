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
import xenagos.application.port.input.admin.AdminMediaTypesUseCase
import xenagos.application.port.input.admin.model.AdminMediaTypeNewRequestDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeResponseDTO
import xenagos.application.port.input.admin.model.AdminMediaTypeUpdateRequestDTO

@SpringBootTest(classes = [PrototypeApplication::class])
@AutoConfigureMockMvc
class AdminMediaTypesControllerIT : BaseWebIT() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var useCase: AdminMediaTypesUseCase

    private val endPoint = "/admin/media-types"

    private fun create(entityName: String, active: Boolean) =
        useCase.saveOneNew(
            AdminMediaTypeNewRequestDTO(
                entityName = entityName,
                active = active
            )
        )

    // GET tests
    @Test
    fun `GET -With one existing entry`() {
        val created = create("Video", true)

        val mvcResult = mockMvc.perform(get(endPoint))
            .andExpect(status().isOk)
            .andExpect(model().attributeExists("listAllModel", "addOneNewModel", "updateOneModel"))
            .andReturn()

        @Suppress("UNCHECKED_CAST")
        val listAllModel = mvcResult.modelAndView!!.model["listAllModel"] as List<AdminMediaTypeResponseDTO>
        val found = listAllModel.firstOrNull { it.id == created.id }
        assertThat(found).isNotNull
        assertThat(found!!.entityName).isEqualTo(created.entityName)
        assertThat(found.active).isEqualTo(created.active)

        val addOneNew = mvcResult.modelAndView!!.model["addOneNewModel"] as AdminMediaTypeNewRequestDTO
        assertThat(addOneNew.entityName).isEmpty()
        assertThat(addOneNew.active).isFalse()

        val updateOne = mvcResult.modelAndView!!.model["updateOneModel"] as AdminMediaTypeUpdateRequestDTO
        assertThat(updateOne.id).isNotNull
        assertThat(updateOne.entityName).isEmpty()
        assertThat(updateOne.active).isFalse()
    }

    @Test
    fun `GET -After adding two more entries`() {
        val created2 = create("Audio", true)
        val created3 = create("Text", false)

        val mvcResult = mockMvc.perform(get(endPoint))
            .andExpect(status().isOk)
            .andExpect(model().attributeExists("listAllModel"))
            .andReturn()

        @Suppress("UNCHECKED_CAST")
        val listAllModel = mvcResult.modelAndView!!.model["listAllModel"] as List<AdminMediaTypeResponseDTO>

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
        val nameParam = "Podcast"
        val existCountBefore = useCase.getAll().count()

        mockMvc.perform(
            post(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("entityName", nameParam)
                .param("active", "true")
        )
            .andExpect(status().isCreated)
            .andExpect(header().string("HX-Redirect", endPoint))

        val existCountAfter = useCase.getAll().count()
        assertThat(existCountAfter).isEqualTo(existCountBefore + 1)

        val created = useCase.getAll().firstOrNull { it.entityName == nameParam }
        assertThat(created).isNotNull
    }

    @Test
    fun `POST -Add valid entry with active=FALSE (missing)`() {
        val nameParam = "Blog"
        val existCountBefore = useCase.getAll().count()

        mockMvc.perform(
            post(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("entityName", nameParam)
        )
            .andExpect(status().isCreated)
            .andExpect(header().string("HX-Redirect", endPoint))

        val existCountAfter = useCase.getAll().count()
        assertThat(existCountAfter).isEqualTo(existCountBefore + 1)

        val created = useCase.getAll().firstOrNull { it.entityName == nameParam }
        assertThat(created).isNotNull
        assertThat(created!!.active).isFalse()
    }

    @Test
    fun `POST -Add entry with invalid 'name' param`() {
        val existCountBefore = useCase.getAll().count()

        val mvcResult = mockMvc.perform(
            post(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("entityName", "")
                .param("active", "true")
        )
            .andExpect(status().isUnprocessableEntity)
            .andReturn()

        assertThat(mvcResult.response.contentAsString).contains("is-invalid")

        val existCountAfter = useCase.getAll().count()
        assertThat(existCountAfter).isEqualTo(existCountBefore)
    }

    // PUT tests
    @Test
    fun `PUT -all new params valid, dont change active=TRUE`() {
        val created = create("Magazine", true)

        val newName = "E-Magazine"

        mockMvc.perform(
            put(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("entityName", newName)
                .param("active", "true")
        )
            .andExpect(status().isOk)
            .andExpect(header().string("HX-Redirect", endPoint))

        val after = useCase.getAll().first { it.id == created.id }
        assertThat(after.entityName).isEqualTo(newName)
        assertThat(after.active).isTrue()
    }

    @Test
    fun `PUT -only change active from TRUE to FALSE (missing)`() {
        val created = create("Newsletter", true)

        mockMvc.perform(
            put(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("entityName", created.entityName)
        )
            .andExpect(status().isOk)
            .andExpect(header().string("HX-Redirect", endPoint))

        val after = useCase.getAll().first { it.id == created.id }
        assertThat(after.active).isFalse()
        assertThat(after.entityName).isEqualTo(created.entityName)
    }

    @Test
    fun `PUT -change 'name' param with invalid`() {
        val created = create("Handout", true)

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

        assertThat(mvcResult.response.contentAsString).contains("is-invalid")

        val after = useCase.getAll().first { it.id == created.id }
        assertThat(after.entityName).isEqualTo(created.entityName)
        assertThat(after.active).isEqualTo(created.active)
    }

    // DELETE tests
    @Test
    fun `DELETE -valid entry`() {
        val created = create("Flyer", true)

        val countBefore = useCase.getAll().count()

        mockMvc.perform(
            delete(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
        )
            .andExpect(status().isNoContent)
            .andExpect(header().string("HX-Redirect", endPoint))

        val countAfter = useCase.getAll().count()
        assertThat(countAfter).isEqualTo(countBefore - 1)
    }
}
