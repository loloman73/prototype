package xenagos.adapter.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import xenagos.application.domain.xenagisi.entity.XenUUID
import xenagos.application.port.commun.model.LightXenDTO
import xenagos.application.port.input.GetLightXensForAreaInPort

@Controller
class LoadLightXensForAreaController( @Autowired private val loadLightXensForAreaService: GetLightXensForAreaInPort) {


    @GetMapping("/{area}")
    fun testThyme(@PathVariable("area") area: Int, model: Model): String{

        val lightXensForArea: List<LightXenDTO>? = loadLightXensForAreaService.whereAreaIs(area)

        var xenIdList = mutableListOf<XenUUID>()
        lightXensForArea?.forEach {xenIdList.add(it.xenId)  }


        model.addAttribute("area", area)
        model.addAttribute("XenID", xenIdList)
        model.addAttribute("Xens", lightXensForArea)

        return "index"
    }


}