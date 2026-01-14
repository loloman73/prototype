package xenagos.adapter.input.web.xenagos

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import kotlin.collections.listOf

class Tour(val id: String, val description: String, val region: String, val status: String)
class TourPublished(val id: String, val description: String, val region: String, publishedDate: String)

@Controller
@RequestMapping("/xenagos/my-tours")
class XenagosMyTours {

    fun myToursAll(): List<Tour> {
        val tour1 = Tour("T-11111", "Tour 1 description", "Greece", "Published")
        val tour2 = Tour("T-22222", "Tour 2 description", "Egypt", "Published")
        val tour3 = Tour("T-2a2a2a2a2a", "Tour 2a description", "Peru", "Unpublished")
        val tour4 = Tour("T-3a3a3a3a3a", "Tour 3a description", "Greece", "Unpublished")
        val tour5 = Tour("T-3b3b3b3b", "Tour 3b description", "Egypt", "Deactivated")
        return  listOf(tour1, tour2, tour3, tour4, tour5)
    }
    fun myToursPublished(): List<TourPublished> {
        val tour1 = TourPublished("T-11-pub", "Tour 1 description", "Greece", "13/01/2024")
        val tour2 = TourPublished("T-22-pub", "Tour 2 description", "Greece", "23/01/2025")
        return  listOf(tour1, tour2)
    }


    @GetMapping
    fun showMyTours(model: Model, @RequestParam status: String?): String {

        if (status == "all") {
            model.addAttribute("myToursAll", myToursAll())
            return "pages/xenagos/xenagosMyToursAll"
        }
        if (status == "published") {
            model.addAttribute("myToursPublished", myToursPublished())
            return "pages/xenagos/xenagosMyToursPublished"
        }
        if (status == "unpublished") {
            return "pages/xenagos/xenagosMyToursUnpublished"
        }
        if (status == "deactivated") {
            return "pages/xenagos/xenagosMyToursDeactivated"
        }

        return "redirect:/xenagos/my-tours?status=all"
    }

}