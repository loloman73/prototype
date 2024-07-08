import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import port.output.LoadLightXensForAreaPort

@Configuration
class PrototypeConfiguration {
    @Bean
    fun loadXensForArea(): LoadLightXensForAreaPort {
        return LoadLightXensForAreaHardCopyAdapter()
    }

}