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
import xenagos.application.port.input.admin.AdminAmeaTagsUseCase
import xenagos.application.port.input.admin.model.AdminAmeaTagNewRequestDTO
import xenagos.application.port.input.admin.model.AdminAmeaTagResponseDTO
import xenagos.application.port.input.admin.model.AdminAmeaTagUpdateRequestDTO

@SpringBootTest(classes = [PrototypeApplication::class])
@AutoConfigureMockMvc
class AdminAmeaTagsControllerIT : BaseWebIT() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var useCase: AdminAmeaTagsUseCase

    private val endPoint = "/admin/amea-tags"
    private fun create(entityName: String, description: String, active: Boolean) =
        useCase.saveOneNew(
            AdminAmeaTagNewRequestDTO(
                entityName = entityName,
                description = description,
                active = active
            )
        )

    //GET tests
    @Test
    fun `GET -With one existing entry`() {

        // Arrange: add 1 valid entry using the UseCase
        val created = create(
            entityName = "Wheelchair Accessible",
            description = "Suitable for wheelchair users",
            active = true
        )

        // Act: call the GET endpoint
        // Expect: HTTP status code, model attributes names
        val mvcResult = mockMvc.perform(get(endPoint))
            .andExpect(status().isOk)
            .andExpect(model().attributeExists("listAllModel", "addOneNewModel", "updateOneModel"))
            .andReturn()

        // Assert: listAllModel contains the created entry with identical parameters
        @Suppress("UNCHECKED_CAST")
        val listAllModel = mvcResult.modelAndView!!.model["listAllModel"] as List<AdminAmeaTagResponseDTO>
        val found = listAllModel.firstOrNull { it.id == created.id }
        assertThat(found).withFailMessage("Expected created entry to appear in listAllModel").isNotNull
        assertThat(found!!.entityName).isEqualTo(created.entityName)
        assertThat(found.description).isEqualTo(created.description)
        assertThat(found.active).isEqualTo(created.active)

        // Assert: addOneNewModel is an empty DTO
        val addOneNew = mvcResult.modelAndView!!.model["addOneNewModel"] as AdminAmeaTagNewRequestDTO
        assertThat(addOneNew.entityName).isEmpty()
        assertThat(addOneNew.description).isEmpty()
        assertThat(addOneNew.active).isFalse()

        // Assert: updateOneModel is an empty DTO
        val updateOne = mvcResult.modelAndView!!.model["updateOneModel"] as AdminAmeaTagUpdateRequestDTO
        assertThat(updateOne.id).isNotNull
        assertThat(updateOne.entityName).isEmpty()
        assertThat(updateOne.description).isEmpty()
        assertThat(updateOne.active).isFalse()
    }

    @Test
    fun `GET -After adding two more entries`() {

        // Arrange: add 2 more valid entries using the UseCase
        val created2 = create(
            entityName = "Braille Signage",
            description = "Includes braille on signs",
            active = true
        )
        val created3 = create(
            entityName = "Assistive Listening",
            description = "Supports assistive listening devices",
            active = false
        )

        // Act: call the GET endpoint
        // Expect: HTTP status code, model attribute name
        val mvcResult = mockMvc.perform(get(endPoint))
            .andExpect(status().isOk)
            .andExpect(model().attributeExists("listAllModel"))
            .andReturn()

        // Assert listAllModel contains both newly created entries with identical fields
        @Suppress("UNCHECKED_CAST")
        val listAllModel = mvcResult.modelAndView!!.model["listAllModel"] as List<AdminAmeaTagResponseDTO>

        val second = listAllModel.firstOrNull { it.id == created2.id }
        assertThat(second).withFailMessage("Expected second entry to appear in listAllModel").isNotNull
        assertThat(second!!.entityName).isEqualTo(created2.entityName)
        assertThat(second.description).isEqualTo(created2.description)
        assertThat(second.active).isEqualTo(created2.active)

        val third = listAllModel.firstOrNull { it.id == created3.id }
        assertThat(third).withFailMessage("Expected third entry to appear in listAllModel").isNotNull
        assertThat(third!!.entityName).isEqualTo(created3.entityName)
        assertThat(third.description).isEqualTo(created3.description)
        assertThat(third.active).isEqualTo(created3.active)
    }


    //POST tests TODO: when params are missing
    @Test
    fun `POST -Add one valid entry with active=TRUE`() {

        val nameParam = "Test Post Request 1"
        val existCountBefore = useCase.getAll().count()

        // Act: call the POST endpoint
        // Expect: HTTP status code, header HX-Redirect to admin page
        mockMvc.perform(
            post(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("entityName", nameParam)
                .param("description", "Test Post Request description")
                .param("active", "true")
        )
            .andExpect(status().isCreated)
            .andExpect(header().string("HX-Redirect", endPoint))

        // Assert only one created
        val existCountAfter = useCase.getAll().count()
        assertThat(existCountAfter).isEqualTo(existCountBefore + 1)

        // Assert entry created is same as posted
        val created = useCase.getAll().firstOrNull { it.entityName == nameParam }
        assertThat(created).withFailMessage("Expected created entry to appear in listAllModel").isNotNull
    }

    @Test
    fun `POST -Add valid entry with active=FALSE (missing)`() {

        val nameParam = "Test Post Request 2"
        val existCountBefore = useCase.getAll().count()

        // Act: call the POST endpoint
        // Expect: HTTP status code, header HX-Redirect to admin page
        mockMvc.perform(
            post(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("entityName", nameParam)
                .param("description", "Test Post Request description")
        )
            .andExpect(status().isCreated)
            .andExpect(header().string("HX-Redirect", endPoint))

        // Assert only one created
        val existCountAfter = useCase.getAll().count()
        assertThat(existCountAfter).isEqualTo(existCountBefore + 1)

        // Assert entry created is same as posted
        val created = useCase.getAll().firstOrNull { it.entityName == nameParam }
        assertThat(created).withFailMessage("Expected created entry to appear in listAllModel").isNotNull
        assertThat(created!!.active).isFalse()
    }

    @Test
    fun `POST -Add entry with invalid 'name' param`() {

        val existCountBefore = useCase.getAll().count()

        // Act: call the POST endpoint
        // Expect: HTTP status code
        val mvcResult = mockMvc.perform(
            post(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("entityName", "")
                .param("description", "This is a valid description")
                .param("active", "true")
        )
            .andExpect(status().isUnprocessableEntity)
            .andReturn()

        // Assert response body (form fragment) contains text "is-invalid"
        assertThat(mvcResult.response.contentAsString).containsOnlyOnce("is-invalid")

        // Assert entry not created
        val existCountAfter = useCase.getAll().count()
        assertThat(existCountAfter).isEqualTo(existCountBefore)
    }

    @Test
    fun `POST -Add entry with invalid 'description' param`() {

        val existCountBefore = useCase.getAll().count()

        // Act: call the POST endpoint
        // Expect: HTTP status code
        val mvcResult = mockMvc.perform(
            post(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("entityName", "Valid Name")
                .param("description", "")
                .param("active", "true")
        ).andExpect(status().isUnprocessableEntity)

        // Assert response body (form fragment) contains text "is-invalid"
        assertThat(mvcResult.andReturn().response.contentAsString).containsOnlyOnce("is-invalid")

        // Assert entry not created
        val existCountAfter = useCase.getAll().count()
        assertThat(existCountAfter).isEqualTo(existCountBefore)
    }

    @Test
    fun `POST -Add entry with all params invalid`() {

        val existCountBefore = useCase.getAll().count()

        // Act: call the POST endpoint
        // Expect: HTTP status code
        val mvcResult = mockMvc.perform(
            post(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("entityName", "")
                .param("description", "")
                .param("active", "true")
        ).andExpect(status().isUnprocessableEntity)

        // Assert response body (form fragment) contains text "is-invalid" twice
        assertThat(mvcResult.andReturn().response.contentAsString)
            .contains("is-invalid")

        // Assert entry not created
        val existCountAfter = useCase.getAll().count()
        assertThat(existCountAfter).isEqualTo(existCountBefore)
    }

    //PUT tests TODO: when params are missing
    @Test
    fun `PUT -all new params valid, dont change active=TRUE`() {

        // Arrange: add 1 valid entry using the UseCase
        val created = create(
            entityName = "Wheelchair Accessible",
            description = "Suitable for wheelchair users",
            active = true
        )

        val newName = "Updated Name"
        val newDescription = "Updated Description"

        // Act: call the PUT endpoint
        // Expect: HTTP status code, header HX-Redirect to admin page
        mockMvc.perform(
            put(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("entityName", newName)
                .param("description", newDescription)
                .param("active", created.active.toString())
        )
            .andExpect(status().isOk)
            .andExpect(header().string("HX-Redirect", endPoint))

        // Assert entry updated as expected
        val updated = useCase.getAll().firstOrNull { it.id == created.id }
        assertThat(updated).isNotNull
        assertThat(updated!!.entityName).isEqualTo(newName)
        assertThat(updated.description).isEqualTo(newDescription)
        assertThat(updated.active).isEqualTo(created.active)
    }

    @Test
    fun `PUT -only change active from TRUE to FALSE (missing)`() {

        // Arrange: add 1 valid entry using the UseCase
        val created = create(
            entityName = "Braille Signage",
            description = "Includes braille on signs",
            active = true
        )

        // Act: call the PUT endpoint
        // Expect: HTTP status code, header HX-Redirect to admin page
        mockMvc.perform(
            put(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("entityName", created.entityName)
                .param("description", created.description)
                // active param is missing -> expect default false
        )
            .andExpect(status().isOk)
            .andExpect(header().string("HX-Redirect", endPoint))

        // Assert entry updated as expected (active becomes false)
        val updated = useCase.getAll().firstOrNull { it.id == created.id }
        assertThat(updated).isNotNull
        assertThat(updated!!.entityName).isEqualTo(created.entityName)
        assertThat(updated.description).isEqualTo(created.description)
        assertThat(updated.active).isFalse()
    }

    @Test
    fun `PUT -change 'name' param with invalid`() {

        // Arrange: add 1 valid entry using the UseCase
        val created = create(
            entityName = "Assistive Listening",
            description = "Supports assistive listening devices",
            active = true
        )

        // Act: call the PUT endpoint
        // Expect: HTTP status code
        val mvcResult = mockMvc.perform(
            put(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("entityName", "")
                .param("description", created.description)
                .param("active", created.active.toString())
        )
            .andExpect(status().isUnprocessableEntity)
            .andReturn()

        // Assert response body (form fragment) contains text "is-invalid"
        assertThat(mvcResult.response.contentAsString).containsOnlyOnce("is-invalid")

        // Assert entry not updated
        val updated = useCase.getAll().firstOrNull { it.id == created.id }
        assertThat(updated).isNotNull
        assertThat(updated!!.entityName).isEqualTo(created.entityName)
        assertThat(updated.description).isEqualTo(created.description)
        assertThat(updated.active).isEqualTo(created.active)
    }

    @Test
    fun `PUT -change 'description' param with invalid`() {

        // Arrange: add 1 valid entry using the UseCase
        val created = create(
            entityName = "High Contrast",
            description = "High contrast visuals",
            active = true
        )

        // Act: call the PUT endpoint
        // Expect: HTTP status code
        val mvcResult = mockMvc.perform(
            put(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("entityName", created.entityName)
                .param("description", "")
                .param("active", created.active.toString())
        )
            .andExpect(status().isUnprocessableEntity)
            .andReturn()

        // Assert response body (form fragment) contains text "is-invalid"
        assertThat(mvcResult.response.contentAsString).containsOnlyOnce("is-invalid")

        // Assert entry not updated
        val updated = useCase.getAll().firstOrNull { it.id == created.id }
        assertThat(updated).isNotNull
        assertThat(updated!!.entityName).isEqualTo(created.entityName)
        assertThat(updated.description).isEqualTo(created.description)
        assertThat(updated.active).isEqualTo(created.active)
    }

    @Test
    fun `PUT -change all params with invalid`() {

        // Arrange: add 1 valid entry using the UseCase
        val created = create(
            entityName = "Captioned Video",
            description = "Contains captions for audio",
            active = true
        )

        // Act: call the PUT endpoint
        // Expect: HTTP status code
        val mvcResult = mockMvc.perform(
            put(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("entityName", "")
                .param("description", "")
                .param("active", created.active.toString())
        )
            .andExpect(status().isUnprocessableEntity)
            .andReturn()

        // Assert response body (form fragment) contains text "is-invalid"
        assertThat(mvcResult.response.contentAsString).contains("is-invalid")

        // Assert entry not updated
        val updated = useCase.getAll().firstOrNull { it.id == created.id }
        assertThat(updated).isNotNull
        assertThat(updated!!.entityName).isEqualTo(created.entityName)
        assertThat(updated.description).isEqualTo(created.description)
        assertThat(updated.active).isEqualTo(created.active)
    }

    //DELETE tests TODO: when params are missing
    @Test
    fun `DELETE -valid entry`() {

        // Arrange: add 1 valid entry using the UseCase
        val created = create(
            entityName = "Sign Language Support",
            description = "Support for sign language",
            active = true
        )

        val existCountBefore = useCase.getAll().count()

        // Act: call the DELETE endpoint
        // Expect: HTTP status code, header HX-Redirect to admin page
        mockMvc.perform(
            delete("$endPoint/${created.id}")
                .contentType(MediaType.TEXT_HTML)
                .header("HX-Request", "true")
        )
            .andExpect(status().isNoContent)
            .andExpect(header().string("HX-Redirect", endPoint))

        // Assert only one deleted
        val existCountAfter = useCase.getAll().count()
        assertThat(existCountAfter).isEqualTo(existCountBefore - 1)

        // Assert entry deleted
        val deleted = useCase.getAll().firstOrNull { it.id == created.id }
        assertThat(deleted).isNull()
    }
}