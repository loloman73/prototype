package xenagos.adapter.output.persistence

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootConfiguration
@EntityScan(basePackages = ["xenagos.adapter.output.persistence.model"])
@EnableJpaRepositories(basePackages = ["xenagos.adapter.output.persistence.admin"])
open class TestApplication
