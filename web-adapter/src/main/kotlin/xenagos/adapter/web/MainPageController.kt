package xenagos.adapter.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import xenagos.application.port.commun.model.LightTourDTO
import xenagos.application.port.input.GetLightToursForAreaInPort

@Controller
class MainPageController(private val loadLightToursForAreaService: GetLightToursForAreaInPort) {

    /* works for area=51 */
    @GetMapping("/area/{area}")
    fun testThyme(@PathVariable("area") area: Int, model: Model): String{

        val lightToursForArea: List<LightTourDTO>? = loadLightToursForAreaService.whereAreaIs(area)

//        val tourIdList = mutableListOf<TourUUID>()
//        lightToursForArea?.forEach {tourIdList.add(it.tourId)  }

        model.addAttribute("area", area)
        model.addAttribute("tours", lightToursForArea)

        return "main"
    }


}