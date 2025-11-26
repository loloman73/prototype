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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
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

    
    //GET tests
    @Test
    fun `GET -With one existing entry`() {

        // Arrange: add 1 valid entry using the UseCase
        val created = create(
            name = "Wheelchair Accessible",
            description = "Suitable for wheelchair users",
            active = true
        )

        // Act: call the GET endpoint
        // Expect: HTTP status code, model attributes names
        val mvcResult = mockMvc.perform(get("/admin/accessibilityTags"))
            .andExpect(status().isOk)
            .andExpect(model().attributeExists("listAllModel", "addOneNewModel", "updateOneModel"))
            .andReturn()

        // Assert: listAllModel contains the created entry with identical parameters
        @Suppress("UNCHECKED_CAST")
        val listAllModel = mvcResult.modelAndView!!.model["listAllModel"] as List<AdminAccessibilityTagResponseDTO>
        val found = listAllModel.firstOrNull { it.id == created.id }
        assertThat(found).withFailMessage("Expected created entry to appear in listAllModel").isNotNull
        assertThat(found!!.name).isEqualTo(created.name)
        assertThat(found.description).isEqualTo(created.description)
        assertThat(found.active).isEqualTo(created.active)

        // Assert: addOneNewModel is an empty DTO
        val addOneNew = mvcResult.modelAndView!!.model["addOneNewModel"] as AdminAccessibilityTagNewRequestDTO
        assertThat(addOneNew.name).isEmpty()
        assertThat(addOneNew.description).isEmpty()
        assertThat(addOneNew.active).isFalse()

        // Assert: updateOneModel is an empty DTO
        val updateOne = mvcResult.modelAndView!!.model["updateOneModel"] as AdminAccessibilityTagUpdateRequestDTO
        assertThat(updateOne.id).isNotNull
        assertThat(updateOne.name).isEmpty()
        assertThat(updateOne.description).isEmpty()
        assertThat(updateOne.active).isFalse()
    }

    @Test
    fun `GET -After adding two more entries`() {

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
        // Expect: HTTP status code, model attribute name
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

    
    //TODO: when params are missing    
    //POST tests
    @Test
    fun `POST -Add one valid entry with active=TRUE`() {

        val nameParam = "Test Post Request 1"
        val existCountBefore = useCase.getAll().count()

        // Act: call the POST endpoint
        // Expect: HTTP status code, header HX-Redirect to admin page
        mockMvc.perform(
            post("/admin/accessibilityTags/addNew")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("name", nameParam)
                .param("description", "Test Post Request description")
                .param("active", "true")
        )
            .andExpect(status().isCreated)
            .andExpect(header().string("HX-Redirect", "/admin/accessibilityTags"))

        // Assert only one created
        val existCountAfter = useCase.getAll().count()
        assertThat(existCountAfter).isEqualTo(existCountBefore + 1)

        // Assert entry created is same as posted
        val created = useCase.getAll().firstOrNull { it.name == nameParam }
        assertThat(created).withFailMessage("Expected created entry to appear in listAllModel").isNotNull
    }

    @Test
    fun `POST -Add valid entry with active=FALSE (missing)`() {

        val nameParam = "Test Post Request 2"
        val existCountBefore = useCase.getAll().count()

        // Act: call the POST endpoint
        // Expect: HTTP status code, header HX-Redirect to admin page
        mockMvc.perform(
            post("/admin/accessibilityTags/addNew")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("name", nameParam)
                .param("description", "Test Post Request description")
        )
            .andExpect(status().isCreated)
            .andExpect(header().string("HX-Redirect", "/admin/accessibilityTags"))

        // Assert only one created
        val existCountAfter = useCase.getAll().count()
        assertThat(existCountAfter).isEqualTo(existCountBefore + 1)

        // Assert entry created is same as posted
        val created = useCase.getAll().firstOrNull { it.name == nameParam }
        assertThat(created).withFailMessage("Expected created entry to appear in listAllModel").isNotNull
        assertThat(created!!.active).isFalse()
    }

    @Test
    fun `POST -Add entry with invalid 'name' param`() {

        val existCountBefore = useCase.getAll().count()

        // Act: call the POST endpoint
        // Expect: HTTP status code
        val mvcResult = mockMvc.perform(
            post("/admin/accessibilityTags/addNew")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("name", "")
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
            post("/admin/accessibilityTags/addNew")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("name", "Valid Name")
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
        val mvcResult = mockMvc.perform(
            post("/admin/accessibilityTags/addNew")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("name", "")
                .param("description", "")
                .param("active", "true")
        )
            .andExpect(status().isUnprocessableEntity)
            .andReturn()

        // Assert response body (form fragment) contains text "is-invalid" 2 times
        assertThat(mvcResult.response.contentAsString.split("is-invalid").size-1).isEqualTo(2)

        // Assert entry not created
        val existCountAfter = useCase.getAll().count()
        assertThat(existCountAfter).isEqualTo(existCountBefore)
    }

    
    //TODO: when params are missing
    //PUT tests
    @Test
    fun `PUT -all new params valid, dont change active=TRUE `() {

        // Arrange: create an entry with active=true
        val created = create(
            name = "Update Me",
            description = "Original description",
            active = true
        )

        val newName = "Updated Name"
        val newDesc = "Updated description"

        // Act: call the PUT endpoint with all valid fields and keep active=true
        // Expect: HTTP status code, header HX-Redirect to admin page
        mockMvc.perform(
            put("/admin/accessibilityTags/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("name", newName)
                .param("description", newDesc)
                .param("active", "true")
        )
            .andExpect(status().isOk)
            .andExpect(header().string("HX-Redirect", "/admin/accessibilityTags"))

        // Assert: entity updated
        val after = useCase.getAll().first { it.id == created.id }
        assertThat(after.name).isEqualTo(newName)
        assertThat(after.description).isEqualTo(newDesc)
        assertThat(after.active).isTrue()
    }

    @Test
    fun `PUT -only change active from TRUE to FALSE (missing)`() {

        // Arrange: create an entry with active=true
        val created = create(
            name = "Toggle Active",
            description = "Keep other fields",
            active = true
        )

        // Act: call the PUT endpoint changing only active to false, keep other fields same
        // Expect: HTTP status code, header HX-Redirect to admin page
        mockMvc.perform(
            put("/admin/accessibilityTags/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("name", created.name)
                .param("description", created.description)
        )
            .andExpect(status().isOk)
            .andExpect(header().string("HX-Redirect", "/admin/accessibilityTags"))

        // Assert: only active changed
        val after = useCase.getAll().first { it.id == created.id }
        assertThat(after.name).isEqualTo(created.name)
        assertThat(after.description).isEqualTo(created.description)
        assertThat(after.active).isFalse()
    }

    @Test
    fun `PUT -change 'name' param with invalid`() {

        // Arrange
        val created = create(
            name = "Valid Name",
            description = "Valid description",
            active = true
        )

        // Act: invalid name (blank)
        // Expect: HTTP status code
        val mvcResult = mockMvc.perform(
            put("/admin/accessibilityTags/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("name", "")
                .param("description", created.description)
                .param("active", created.active.toString())
        )
            .andExpect(status().isUnprocessableEntity)
            .andReturn()

        // Assert response body (fragment) contains one invalid marker
        assertThat(mvcResult.response.contentAsString).containsOnlyOnce("is-invalid")

        // Assert not updated
        val after = useCase.getAll().first { it.id == created.id }
        assertThat(after.name).isEqualTo(created.name)
        assertThat(after.description).isEqualTo(created.description)
        assertThat(after.active).isEqualTo(created.active)
    }

    @Test
    fun `PUT -change 'description' param with invalid`() {

        // Arrange
        val created = create(
            name = "Another Valid Name",
            description = "Initial description",
            active = true
        )

        // Act: invalid description (blank)
        // Expect: HTTP status code
        val mvcResult = mockMvc.perform(
            put("/admin/accessibilityTags/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("name", created.name)
                .param("description", "")
                .param("active", created.active.toString())
        )
            .andExpect(status().isUnprocessableEntity)
            .andReturn()

        // Assert response body (fragment) contains one invalid marker
        assertThat(mvcResult.response.contentAsString).containsOnlyOnce("is-invalid")

        // Assert not updated
        val after = useCase.getAll().first { it.id == created.id }
        assertThat(after.name).isEqualTo(created.name)
        assertThat(after.description).isEqualTo(created.description)
        assertThat(after.active).isEqualTo(created.active)
    }

    @Test
    fun `PUT -change all params with invalid`() {

        // Arrange
        val created = create(
            name = "Base Name",
            description = "Base description",
            active = true
        )

        // Act: invalid name and description
        val mvcResult = mockMvc.perform(
            put("/admin/accessibilityTags/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("name", "")
                .param("description", "")
                .param("active", created.active.toString())
        )
            .andExpect(status().isUnprocessableEntity)
            .andReturn()

        // Assert response body (fragment) contains text "is-invalid" 2 times
        assertThat(mvcResult.response.contentAsString.split("is-invalid").size - 1).isEqualTo(2)

        // Assert not updated
        val after = useCase.getAll().first { it.id == created.id }
        assertThat(after.name).isEqualTo(created.name)
        assertThat(after.description).isEqualTo(created.description)
        assertThat(after.active).isEqualTo(created.active)
    }
    

}