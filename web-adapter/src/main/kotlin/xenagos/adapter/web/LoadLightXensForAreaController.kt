package xenagos.adapter.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import xenagos.application.port.commun.model.LightXenDTO
import xenagos.application.port.input.GetLightXensForAreaInPort

@Controller
class LoadLightXensForAreaController( @Autowired private val loadLightXensForAreaService: GetLightXensForAreaInPort) {

    @GetMapping("/testStatic")
    fun loadPage():String {
        return "redirect:/test.html"
    }

    @ResponseBody
    @GetMapping("/area/{area}/", produces = ["application/json"])
    fun testReturnJSON(@PathVariable("area") area: Int): List<LightXenDTO>? {

        val lightXensForArea: List<LightXenDTO>? = loadLightXensForAreaService.whereAreaIs(area)

        return lightXensForArea
    }



}