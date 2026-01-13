package xenagos.adapter.input.web.xenagos

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import kotlin.collections.listOf

class Tour(val id: String, val description: String, val region: String, val status: String)

@Controller
@RequestMapping("/xenagos/my-tours")
class XenagosMyTours {

    fun myToursAll(): List<Tour> {
        val tour1 = Tour("T-11111", "Tour 1 description", "Greece", "Published")
        val tour2 = Tour("T-22222", "Tour 2 description", "Egypt", "Unpublished")
        val tour3 = Tour("T-33333", "Tour 3 description", "Peru", "Deactivated")
        return  listOf(tour1, tour2, tour3)
    }
    fun myToursUnpublished(): List<Tour> {
        val tour2 = Tour("T-22222", "Tour 2 description", "Egypt", "Unpublished")
        return  listOf(tour2)
    }
    fun myToursDeactivated(): List<Tour> {
        val tour3 = Tour("T-33333", "Tour 3 description", "Peru", "Deactivated")
        return  listOf(tour3)
    }



    @GetMapping
    fun showMyTours(model: Model, @RequestParam status: String?): String {

        if (status == "published") {
            model.addAttribute("myToursAll", myToursAll())
            return "pages/xenagos/xenagosMyToursAll"
        }
        if (status == "unpublished") {
            model.addAttribute("myToursUnpublilshed", myToursUnpublished())
            return "pages/xenagos/xenagosMyToursUnpublished"
        }
        if (status == "deactivated") {
            model.addAttribute("myToursdeactivated", myToursDeactivated())
            return "pages/xenagos/xenagosMyToursDeactivated"
        }

        return "redirect:/xenagos/my-tours?status=published"
    }

}