package xenagos.adapter.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import xenagos.application.domain.xenagisi.entity.XenUUID
import xenagos.application.port.commun.model.LightXenDTO
import xenagos.application.port.input.GetLightXensForAreaInPort

@Controller
class LoadLightXensForAreaController( @Autowired private val loadLightXensForAreaService: GetLightXensForAreaInPort) {

    /* works for area=51 */
    @GetMapping("/{area}")
    fun testThyme(@PathVariable("area") area: Int, model: Model): String{

        val lightXensForArea: List<LightXenDTO>? = loadLightXensForAreaService.whereAreaIs(area)

        val xenIdList = mutableListOf<XenUUID>()
        lightXensForArea?.forEach {xenIdList.add(it.xenId)  }

        model.addAttribute("area", area)
        model.addAttribute("Xens", lightXensForArea)

        return "index"
    }


}