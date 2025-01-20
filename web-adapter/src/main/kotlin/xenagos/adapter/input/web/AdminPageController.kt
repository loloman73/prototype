package xenagos.adapter.input.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/admin")
class AdminPageAdapter {

    @GetMapping
    fun mainAdminPage():String{
        return "adminMain"
    }

    @GetMapping("/accessibilityTags")
    fun accessibilityTags():String{
        return "adminAccessibilityTags"
    }

    @GetMapping("/ageRanges")
    fun ageRanges():String{
        return "adminAgeRanges"
    }

    @GetMapping("/languages")
    fun languages():String{
        return "adminLanguages"
    }

    @GetMapping("/mediaTypes")
    fun mediaTypes():String{
        return "adminMediaTypes"
    }

    @GetMapping("/durationTypes")
    fun durationTypes():String{
        return "adminDurationTypes"
    }

    @GetMapping("/ameaFriendlyTypes")
    fun ameAFriendlyTypes():String{
        return "adminAmeAFriendlyTypes"
    }

}