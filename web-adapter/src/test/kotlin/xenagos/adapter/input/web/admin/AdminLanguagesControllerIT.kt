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
import xenagos.application.port.input.admin.AdminLanguagesUseCase
import xenagos.application.port.input.admin.model.AdminLanguageNewRequestDTO
import xenagos.application.port.input.admin.model.AdminLanguageResponseDTO
import xenagos.application.port.input.admin.model.AdminLanguageUpdateRequestDTO

@SpringBootTest(classes = [PrototypeApplication::class])
@AutoConfigureMockMvc
class AdminLanguagesControllerIT : BaseWebIT() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var useCase: AdminLanguagesUseCase

    private val endPoint = "/admin/languages"

    private fun create(code: String, englishName: String, nativeName: String, active: Boolean) =
        useCase.saveOneNew(
            AdminLanguageNewRequestDTO(
                code = code,
                englishName = englishName,
                nativeName = nativeName,
                active = active
            )
        )

    // GET tests
    @Test
    fun `GET -With one existing entry`() {
        val created = create("ENG", "English", "English", true)

        val mvcResult = mockMvc.perform(get(endPoint))
            .andExpect(status().isOk)
            .andExpect(model().attributeExists("listAllModel", "addOneNewModel", "updateOneModel"))
            .andReturn()

        @Suppress("UNCHECKED_CAST")
        val listAllModel = mvcResult.modelAndView!!.model["listAllModel"] as List<AdminLanguageResponseDTO>
        val found = listAllModel.firstOrNull { it.id == created.id }
        assertThat(found).isNotNull
        assertThat(found!!.code).isEqualTo(created.code)
        assertThat(found.englishName).isEqualTo(created.englishName)
        assertThat(found.nativeName).isEqualTo(created.nativeName)
        assertThat(found.active).isEqualTo(created.active)

        val addOneNew = mvcResult.modelAndView!!.model["addOneNewModel"] as AdminLanguageNewRequestDTO
        assertThat(addOneNew.code).isEmpty()
        assertThat(addOneNew.englishName).isEmpty()
        assertThat(addOneNew.nativeName).isEmpty()
        assertThat(addOneNew.active).isFalse()

        val updateOne = mvcResult.modelAndView!!.model["updateOneModel"] as AdminLanguageUpdateRequestDTO
        assertThat(updateOne.id).isNotNull
        assertThat(updateOne.code).isEmpty()
        assertThat(updateOne.englishName).isEmpty()
        assertThat(updateOne.nativeName).isEmpty()
        assertThat(updateOne.active).isFalse()
    }

    @Test
    fun `GET -After adding two more entries`() {
        val created2 = create("ELL", "Greek", "Ελληνικά", true)
        val created3 = create("ESP", "Spanish", "Español", false)

        val mvcResult = mockMvc.perform(get(endPoint))
            .andExpect(status().isOk)
            .andExpect(model().attributeExists("listAllModel"))
            .andReturn()

        @Suppress("UNCHECKED_CAST")
        val listAllModel = mvcResult.modelAndView!!.model["listAllModel"] as List<AdminLanguageResponseDTO>

        val second = listAllModel.firstOrNull { it.id == created2.id }
        assertThat(second).isNotNull
        assertThat(second!!.code).isEqualTo(created2.code)
        assertThat(second.englishName).isEqualTo(created2.englishName)
        assertThat(second.nativeName).isEqualTo(created2.nativeName)
        assertThat(second.active).isEqualTo(created2.active)

        val third = listAllModel.firstOrNull { it.id == created3.id }
        assertThat(third).isNotNull
        assertThat(third!!.code).isEqualTo(created3.code)
        assertThat(third.englishName).isEqualTo(created3.englishName)
        assertThat(third.nativeName).isEqualTo(created3.nativeName)
        assertThat(third.active).isEqualTo(created3.active)
    }

    // POST tests
    @Test
    fun `POST -Add one valid entry with active=TRUE`() {
        val code = "FRA"
        val existCountBefore = useCase.getAll().count()

        mockMvc.perform(
            post(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("code", code)
                .param("englishName", "French")
                .param("nativeName", "Français")
                .param("active", "true")
        )
            .andExpect(status().isCreated)
            .andExpect(header().string("HX-Redirect", endPoint))

        val existCountAfter = useCase.getAll().count()
        assertThat(existCountAfter).isEqualTo(existCountBefore + 1)

        val created = useCase.getAll().firstOrNull { it.code == code }
        assertThat(created).isNotNull
    }

    @Test
    fun `POST -Add valid entry with active=FALSE (missing)`() {
        val code = "DEU"
        val existCountBefore = useCase.getAll().count()

        mockMvc.perform(
            post(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("code", code)
                .param("englishName", "German")
                .param("nativeName", "Deutsch")
        )
            .andExpect(status().isCreated)
            .andExpect(header().string("HX-Redirect", endPoint))

        val existCountAfter = useCase.getAll().count()
        assertThat(existCountAfter).isEqualTo(existCountBefore + 1)

        val created = useCase.getAll().firstOrNull { it.code == code }
        assertThat(created).isNotNull
        assertThat(created!!.active).isFalse()
    }

    @Test
    fun `POST -Add entry with invalid 'code' param`() {
        val existCountBefore = useCase.getAll().count()

        val mvcResult = mockMvc.perform(
            post(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("code", "")
                .param("englishName", "Hindi")
                .param("nativeName", "हिन्दी")
                .param("active", "true")
        )
            .andExpect(status().isUnprocessableEntity)
            .andReturn()

        assertThat(mvcResult.response.contentAsString).contains("is-invalid")

        val existCountAfter = useCase.getAll().count()
        assertThat(existCountAfter).isEqualTo(existCountBefore)
    }

    @Test
    fun `POST -Add entry with invalid 'englishName' param`() {
        val existCountBefore = useCase.getAll().count()

        val mvcResult = mockMvc.perform(
            post(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("code", "ITA")
                .param("englishName", "")
                .param("nativeName", "Italiano")
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
                .param("code", "")
                .param("englishName", "")
                .param("nativeName", "")
                .param("active", "true")
        )
            .andExpect(status().isUnprocessableEntity)
            .andReturn()

        assertThat(mvcResult.response.contentAsString.split("is-invalid").size - 1).isGreaterThanOrEqualTo(3)

        val existCountAfter = useCase.getAll().count()
        assertThat(existCountAfter).isEqualTo(existCountBefore)
    }

    // PUT tests
    @Test
    fun `PUT -all new params valid, dont change active=TRUE`() {
        val created = create("POR", "Portuguese", "Português", true)

        val newCode = "PRT"
        val newEn = "Portuguese (EU)"
        val newNative = "Português (EU)"

        mockMvc.perform(
            put(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("code", newCode)
                .param("englishName", newEn)
                .param("nativeName", newNative)
                .param("active", "true")
        )
            .andExpect(status().isOk)
            .andExpect(header().string("HX-Redirect", endPoint))

        val after = useCase.getAll().first { it.id == created.id }
        assertThat(after.code).isEqualTo(newCode)
        assertThat(after.englishName).isEqualTo(newEn)
        assertThat(after.nativeName).isEqualTo(newNative)
        assertThat(after.active).isTrue()
    }

    @Test
    fun `PUT -only change active from TRUE to FALSE (missing)`() {
        val created = create("NLD", "Dutch", "Nederlands", true)

        mockMvc.perform(
            put(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("code", created.code)
                .param("englishName", created.englishName)
                .param("nativeName", created.nativeName)
        )
            .andExpect(status().isOk)
            .andExpect(header().string("HX-Redirect", endPoint))

        val after = useCase.getAll().first { it.id == created.id }
        assertThat(after.active).isFalse()
        assertThat(after.code).isEqualTo(created.code)
        assertThat(after.englishName).isEqualTo(created.englishName)
        assertThat(after.nativeName).isEqualTo(created.nativeName)
    }

    @Test
    fun `PUT -change 'code' param with invalid`() {
        val created = create("JPN", "Japanese", "日本語", true)

        val mvcResult = mockMvc.perform(
            put(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("code", "")
                .param("englishName", created.englishName)
                .param("nativeName", created.nativeName)
                .param("active", created.active.toString())
        )
            .andExpect(status().isUnprocessableEntity)
            .andReturn()

        assertThat(mvcResult.response.contentAsString).contains("is-invalid")

        val after = useCase.getAll().first { it.id == created.id }
        assertThat(after.code).isEqualTo(created.code)
        assertThat(after.englishName).isEqualTo(created.englishName)
        assertThat(after.nativeName).isEqualTo(created.nativeName)
        assertThat(after.active).isEqualTo(created.active)
    }

    @Test
    fun `PUT -change all params with invalid`() {
        val created = create("KOR", "Korean", "한국어", true)

        val mvcResult = mockMvc.perform(
            put(endPoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("HX-Request", "true")
                .param("id", created.id.toString())
                .param("code", "")
                .param("englishName", "")
                .param("nativeName", "")
                .param("active", created.active.toString())
        )
            .andExpect(status().isUnprocessableEntity)
            .andReturn()

        assertThat(mvcResult.response.contentAsString.split("is-invalid").size - 1).isEqualTo(3)

        val after = useCase.getAll().first { it.id == created.id }
        assertThat(after.code).isEqualTo(created.code)
        assertThat(after.englishName).isEqualTo(created.englishName)
        assertThat(after.nativeName).isEqualTo(created.nativeName)
        assertThat(after.active).isEqualTo(created.active)
    }

    @Test
    fun `DELETE -valid entry`() {
        val created = create("RUS", "Russian", "Русский", true)

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
