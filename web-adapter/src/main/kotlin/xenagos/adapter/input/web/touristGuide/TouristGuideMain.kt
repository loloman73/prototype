package xenagos.adapter.input.web.touristGuide

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/tourist-guide")
class TouristGuideMain {

    @GetMapping
    fun redirectMainPage(): String{
        // if Tourist Guide is logged in, redirect to my tours
        // else redirect to login page
        return "redirect:/tourist-guide/my-tours?status=all"
    }
}