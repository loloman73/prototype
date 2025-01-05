package xenagos.adapter.web

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

    @GetMapping("/AccessibilityTags/")
    fun accessibilityTags():String{
        return "adminAccessibilityTags"
    }

    @GetMapping("/TopicTags/")
    fun topicTags():String{
        return "adminTopicTags"
    }

    @GetMapping("/AgeRanges/")
    fun ageRanges():String{
        return "adminAgeRanges"
    }

    @GetMapping("/Languages/")
    fun languages():String{
        return "adminLanguages"
    }

    @GetMapping("/MediaTypes/")
    fun mediaTypes():String{
        return "adminMediaTypes"
    }

    @GetMapping("/DurationTypes/")
    fun durationTypes():String{
        return "adminDurationTypes"
    }

    @GetMapping("/AmeAFriendlyTypes/")
    fun ameAFriendlyTypes():String{
        return "adminAmeAFriendlyTypes"
    }

}