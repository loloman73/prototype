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
import xenagos.application.port.input.admin.AdminAgeGroupUseCase
import xenagos.application.port.input.admin.model.AdminAgeGroupNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupResponseDTO
import xenagos.application.port.input.admin.model.AdminAgeGroupUpdateRequestDTO

@SpringBootTest(classes = [PrototypeApplication::class])
@AutoConfigureMockMvc
class AdminAgeGroupsControllerIT : BaseWebIT() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var useCase: AdminAgeGroupUseCase

    private val endPoint = "/admin/age-groups"

    private fun create(entityName: String, minAge: Byte, maxAge: Byte, active: Boolean) =
        useCase.saveOneNew(
            AdminAgeGroupNewRequestDTO(
                entityName = entityName,
                minAge = minAge,
                maxAge = maxAge,
                active = active
            )
        )

    // GET tests
    @Test
    fun `GET -With one existing entry`() {

        val created = create(
            entityName = "Kids",
            minAge = 5,
            maxAge = 12,
            active = true
        )

        val mvcResult = mockMvc.perform(get(endPoint))
            .andExpect(status().isOk)
            .andExpect(model().attributeExists("listAllModel", "addOneNewModel", "updateOneModel"))
            .andReturn()

        @Suppress("UNCHECKED_CAST")
        val listAllModel = mvcResult.modelAndView!!.model["listAllModel"] as List<AdminAgeGroupResponseDTO>
        val found = listAllModel.firstOrNull { it.id == created.id }
        assertThat(found).withFailMessage("Expected created entry to appear in listAllModel").isNotNull
        assertThat(found!!.entityName).isEqualTo(created.entityName)
        assertThat(found.minAge).isEqualTo(created.minAge)
        assertThat(found.maxAge).isEqualTo(created.maxAge)
        assertThat(found.active).isEqualTo(created.active)

        val addOneNew = mvcResult.modelAndView!!.model["addOneNewModel"] as AdminAgeGroupNewRequestDTO
        assertThat(addOneNew.entityName).isEmpty()
        assertThat(addOneNew.minAge).isEqualTo(0)
        assertThat(addOneNew.maxAge).isEqualTo(0)
        assertThat(addOneNew.active).isFalse()

        val updateOne = mvcResult.modelAndView!!.model["updateOneModel"] as AdminAgeGroupUpdateRequestDTO
        assertThat(updateOne.id).isNotNull
        assertThat(updateOne.entityName).isEmpty()
        assertThat(updateOne.minAge).isEqualTo(0)
        assertThat(updateOne.maxAge).isEqualTo(0)
        assertThat(updateOne.active).isFalse()
    }

    @Test
    fun `GET -After adding two more entries`() {

        val created2 = create("Teens", 13, 17, true)
        val created3 = create("Adults", 18, 64, false)

        val mvcResult = mockMvc.perform(get(endPoint))
            .andExpect(status().isOk)
            .andExpect(model().attributeExists("listAllModel"))
            .andReturn()

        @Suppress("UNCHECKED_CAST")
        val listAllModel = mvcResult.modelAndView!!.model["listAllModel"] as List<AdminAgeGroupResponseDTO>

        val second = listAllModel.firstOrNull { it.id == created2.id }
        assertThat(second).withFailMessage("Expected second entry to appear in listAllModel").isNotNull
        assertThat(second!!.entityName).isEqualTo(created2.entityName)
        assertThat(second.minAge).isEqualTo(created2.minAge)
        assertThat(second.maxAge).isEqualTo(created2.maxAge)
        assertThat(second.active).isEqualTo(created2.active)

        val third = listAllModel.firstOrNull { it.id == created3.id }
        assertThat(third).withFailMessage("Expected third entry to appear in listAllModel").isNotNull
        assertThat(third!!.entityName).isEqualTo(created3.entityName)
        assertThat(third.minAge).isEqualTo(created3.minAge)
        assertThat(third.maxAge).isEqualTo(created3.maxAge)
        assertThat(third.active).isEqualTo(created3.active)
    }

    // POST tests
    @Test
    fun `POST -Add one valid entry with active=TRUE`() {

        val nameParam = "Seniors"
        val existCountBefore = useCase.getAll().count()

        mockMvc.perform(
            post(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("entityName", nameParam)
                .param("minAge", "65")
                .param("maxAge", "99")
                .param("active", "true")
        )
            .andExpect(status().isCreated)
            .andExpect(header().string("HX-Redirect", endPoint))

        val existCountAfter = useCase.getAll().count()
        assertThat(existCountAfter).isEqualTo(existCountBefore + 1)

        val created = useCase.getAll().firstOrNull { it.entityName == nameParam }
        assertThat(created).withFailMessage("Expected created entry to appear in listAllModel").isNotNull
    }

    @Test
    fun `POST -Add valid entry with active=FALSE (missing)`() {

        val nameParam = "Babies"
        val existCountBefore = useCase.getAll().count()

        mockMvc.perform(
            post(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("entityName", nameParam)
                .param("minAge", "1")
                .param("maxAge", "2")
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
    fun `POST -Add entry with invalid 'groupName' param`() {

        val existCountBefore = useCase.getAll().count()

        val mvcResult = mockMvc.perform(
            post(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("entityName", "")
                .param("minAge", "3")
                .param("maxAge", "6")
                .param("active", "true")
        )
            .andExpect(status().isUnprocessableEntity)
            .andReturn()

        assertThat(mvcResult.response.contentAsString).contains("is-invalid")

        val existCountAfter = useCase.getAll().count()
        assertThat(existCountAfter).isEqualTo(existCountBefore)
    }

    @Test
    fun `POST -Add entry with invalid 'minAge' param`() {

        val existCountBefore = useCase.getAll().count()

        val mvcResult = mockMvc.perform(
            post(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("entityName", "Toddlers")
                .param("minAge", "0")
                .param("maxAge", "3")
                .param("active", "true")
        )
            .andExpect(status().isUnprocessableEntity)
            .andReturn()

        assertThat(mvcResult.response.contentAsString).contains("is-invalid")

        val existCountAfter = useCase.getAll().count()
        assertThat(existCountAfter).isEqualTo(existCountBefore)
    }

    @Test
    fun `POST -Add entry with all params invalid`() {

        val existCountBefore = useCase.getAll().count()

        val mvcResult = mockMvc.perform(
            post(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("entityName", "")
                .param("minAge", "0")
                .param("maxAge", "0")
                .param("active", "true")
        )
            .andExpect(status().isUnprocessableEntity)
            .andReturn()

        // Expect all three fields invalid
        assertThat(mvcResult.response.contentAsString.split("is-invalid").size - 1).isEqualTo(3)

        val existCountAfter = useCase.getAll().count()
        assertThat(existCountAfter).isEqualTo(existCountBefore)
    }

    // PUT tests
    @Test
    fun `PUT -all new params valid, dont change active=TRUE`() {

        val created = create("Edit Me", 10, 15, true)

        val newName = "Edited Group"
        val newMin = 11
        val newMax = 16

        mockMvc.perform(
            put(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("entityName", newName)
                .param("minAge", newMin.toString())
                .param("maxAge", newMax.toString())
                .param("active", "true")
        )
            .andExpect(status().isOk)
            .andExpect(header().string("HX-Redirect", endPoint))

        val after = useCase.getAll().first { it.id == created.id }
        assertThat(after.entityName).isEqualTo(newName)
        assertThat(after.minAge).isEqualTo(newMin.toByte())
        assertThat(after.maxAge).isEqualTo(newMax.toByte())
        assertThat(after.active).isTrue()
    }

    @Test
    fun `PUT -only change active from TRUE to FALSE (missing)`() {

        val created = create("Flip Active", 20, 25, true)

        mockMvc.perform(
            put(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("entityName", created.entityName)
                .param("minAge", created.minAge.toString())
                .param("maxAge", created.maxAge.toString())
        )
            .andExpect(status().isOk)
            .andExpect(header().string("HX-Redirect", endPoint))

        val after = useCase.getAll().first { it.id == created.id }
        assertThat(after.active).isFalse()
        assertThat(after.entityName).isEqualTo(created.entityName)
        assertThat(after.minAge).isEqualTo(created.minAge)
        assertThat(after.maxAge).isEqualTo(created.maxAge)
    }

    @Test
    fun `PUT -change 'groupName' param with invalid`() {

        val created = create("Valid Group", 30, 40, true)

        val mvcResult = mockMvc.perform(
            put(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("entityName", "")
                .param("minAge", created.minAge.toString())
                .param("maxAge", created.maxAge.toString())
                .param("active", created.active.toString())
        )
            .andExpect(status().isUnprocessableEntity)
            .andReturn()

        assertThat(mvcResult.response.contentAsString).contains("is-invalid")

        val after = useCase.getAll().first { it.id == created.id }
        assertThat(after.entityName).isEqualTo(created.entityName)
        assertThat(after.minAge).isEqualTo(created.minAge)
        assertThat(after.maxAge).isEqualTo(created.maxAge)
        assertThat(after.active).isEqualTo(created.active)
    }

    @Test
    fun `PUT -change 'minAge' param with invalid`() {

        val created = create("Valid Group", 30, 40, true)

        val mvcResult = mockMvc.perform(
            put(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("entityName", created.entityName)
                .param("minAge", "0")
                .param("maxAge", created.maxAge.toString())
                .param("active", created.active.toString())
        )
            .andExpect(status().isUnprocessableEntity)
            .andReturn()

        assertThat(mvcResult.response.contentAsString).contains("is-invalid")

        val after = useCase.getAll().first { it.id == created.id }
        assertThat(after.entityName).isEqualTo(created.entityName)
        assertThat(after.minAge).isEqualTo(created.minAge)
        assertThat(after.maxAge).isEqualTo(created.maxAge)
        assertThat(after.active).isEqualTo(created.active)
    }

    @Test
    fun `PUT -change all params with invalid`() {

        val created = create("Valid Group", 30, 40, true)

        val mvcResult = mockMvc.perform(
            put(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("entityName", "")
                .param("minAge", "0")
                .param("maxAge", "0")
                .param("active", created.active.toString())
        )
            .andExpect(status().isUnprocessableEntity)
            .andReturn()

        assertThat(mvcResult.response.contentAsString.split("is-invalid").size - 1).isGreaterThanOrEqualTo(3)

        val after = useCase.getAll().first { it.id == created.id }
        assertThat(after.entityName).isEqualTo(created.entityName)
        assertThat(after.minAge).isEqualTo(created.minAge)
        assertThat(after.maxAge).isEqualTo(created.maxAge)
        assertThat(after.active).isEqualTo(created.active)
    }

    @Test
    fun `DELETE -valid entry`() {

        val created = create("Delete Me", 8, 9, true)

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
