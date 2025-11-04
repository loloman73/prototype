package xenagos.adapter.input.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import xenagos.application.port.input.TourDTO
import xenagos.application.port.input.GetToursForAreaUseCase

@Controller
class MainPageController(private val getToursForArea: GetToursForAreaUseCase) {

    /* works for area=51 */
    @GetMapping("/area/{area}")
    fun mainPage(@PathVariable("area") area: Int, model: Model): String{
        val toursForArea: List<TourDTO>? =  getToursForArea.whereAreaIs(area)
        model.addAttribute("tours", toursForArea)
        return "main"
    }

 }