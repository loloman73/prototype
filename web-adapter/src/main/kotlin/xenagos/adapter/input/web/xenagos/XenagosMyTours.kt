package xenagos.adapter.input.web.xenagos

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/xenagos/my-tours")
class XenagosMyTours {

    @GetMapping
    fun showMyTours(): String{
        return "my-tours"
    }
}