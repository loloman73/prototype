package xenagos.adapter.input.web.admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin/ameaFriendlyTypes")
class AdminAmeaFriendlyTypesController {

    @GetMapping
    fun getAmeaFriendlyTypes(): String {
        return "adminAmeaFriendlyTypes"
    }
}