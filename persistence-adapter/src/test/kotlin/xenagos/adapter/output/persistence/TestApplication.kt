package xenagos.adapter.output.persistence

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

// Minimal `@SpringBootConfiguration` with `@EntityScan` and `@EnableJpaRepositories`
// so @DataJpaTest can bootstrap only what we need and not the entire application context
@SpringBootConfiguration
@EntityScan(basePackages = ["xenagos.adapter.output.persistence.model"])
@EnableJpaRepositories(basePackages = ["xenagos.adapter.output.persistence.admin"])
open class TestApplication
