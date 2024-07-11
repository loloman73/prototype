package xenagos.adapter.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import xenagos.application.port.commun.model.LightXenDTO
import xenagos.application.port.input.GetLightXensForAreaInPort


@RestController
class LoadLightXensForAreaController( @Autowired private val loadLightXensForAreaService: GetLightXensForAreaInPort) {

    @GetMapping("/")
    fun loadPage():String {
        return ("HELLO FROM ROOT")
    }

    @GetMapping("/{area}/")
    fun LoadLightXensForArea(@PathVariable("area") area: Int): String{

        val lightXensForArea: List<LightXenDTO>? = loadLightXensForAreaService.whereAreaIs(area)


        return "$lightXensForArea"
    }



}