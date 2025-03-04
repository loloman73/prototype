package xenagos.adapter.input.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin/ageRanges")
class AdminAgeRangesController {

    @GetMapping
    fun getAgeRanges(): String{
        return "adminAgeRanges"
    }
}