import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import port.output.LoadLightXensForAreaOutPort

@Configuration
class PrototypeConfiguration {
    @Bean
    fun loadXensForArea(): LoadLightXensForAreaOutPort {
        return LoadLightXensForAreaHardEncodedAdapter()
    }

}