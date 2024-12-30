package xenagos.adapter.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/admin")
class AdminPageAdapter {
    @GetMapping
    fun adminPage():String{

        return "admin"
    }

}