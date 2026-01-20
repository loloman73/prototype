package xenagos.adapter.input.web.tourGuide

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/tour-guide")
class TourGuideMain {

    @GetMapping
    fun redirectMainPage(): String{
        // if Tour Guide is logged in, redirect to my tours
        // else redirect to login page
        return "redirect:/tour-guide/my-tours?status=all"
    }
}