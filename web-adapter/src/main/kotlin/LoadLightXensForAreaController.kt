import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import port.input.GetLightXensForAreaInPort
import port.commun.models.LightXenDTO

@RestController
class LoadLightXensForAreaController(private val loadLightXensForAreaService: GetLightXensForAreaInPort) {

    @GetMapping("/loadLightXen/{area}/")
    fun LoadLightXensForArea(@PathVariable("area") area: Int, model:Model): String{

        val lightXensForArea: List<LightXenDTO>? = loadLightXensForAreaService.whereAreaIs(area)

        return "LoadLightXensForArea"
    }

}