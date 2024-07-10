package xenagos.adapter.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class TestResp {
    @GetMapping("/")
    fun loadPage():String {
        return ("HELLO")
    }
}

//class LoadLightXensForAreaController(@Autowired private val loadLightXensForAreaService: GetLightXensForAreaInPort) {
//
//    @GetMapping("/l/{area}/")
//    fun LoadLightXensForArea(@PathVariable("area") area: Int): String{
//
//        val lightXensForArea: List<LightXenDTO>? = loadLightXensForAreaService.whereAreaIs(area)
//
//        return "HELLO"
//    }
//
//}