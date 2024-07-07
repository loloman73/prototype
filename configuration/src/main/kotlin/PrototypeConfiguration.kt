import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import port.output.LoadXensForAreaPort

@Configuration
class PrototypeConfiguration {
    @Bean
    fun loadXensForArea(): LoadXensForAreaPort {
        return LoadXensForAreaAdapter()
    }

}