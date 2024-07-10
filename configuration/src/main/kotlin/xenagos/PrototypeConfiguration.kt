package xenagos

import xenagos.adapter.persistence.LoadLightXensForAreaHardEncodedAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import xenagos.application.port.output.LoadLightXensForAreaOutPort

@Configuration
class PrototypeConfiguration {
    @Bean
    fun loadXensForArea(): LoadLightXensForAreaOutPort {
        return LoadLightXensForAreaHardEncodedAdapter()
    }

}