package xenagos.adapter.input.web.xenagos

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/xenagos")
class XenagosMain {

    @GetMapping
    fun redirectMainPage(): String{
        // if xenagos is logged in, redirect to my tours
        // else redirect to login
        return "redirect:/xenagos/my-tours"
    }
}