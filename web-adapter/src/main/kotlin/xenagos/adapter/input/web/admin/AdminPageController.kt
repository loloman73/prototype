package xenagos.adapter.input.web.admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin")
class AdminPageController {

    @GetMapping
    fun mainAdminPage(): String{
        return BaseAdminController.ADMIN_TEMPLATE_PATH_PREFIX + "adminMain"
    }
}