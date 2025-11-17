package xenagos.adapter.input.web.admin

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

/**
 * Base class to bootstrap a PostgreSQL Testcontainers instance for end-to-end web tests.
 * Registers the datasource properties for the Spring Boot application context.
 */
abstract class BaseWebIT {

    companion object {
        private val image: DockerImageName = DockerImageName.parse("postgres:16-alpine")

        @JvmStatic
        val postgres: PostgreSQLContainer<*> = PostgreSQLContainer(image)
            .withDatabaseName("xenagos_test_web")
            .withUsername("test")
            .withPassword("test")

        @BeforeAll
        @JvmStatic
        fun startContainer() {
            postgres.start()
        }

        @AfterAll
        @JvmStatic
        fun stopContainer() {
            postgres.stop()
        }

        @DynamicPropertySource
        @JvmStatic
        fun registerDataSourceProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { postgres.jdbcUrl }
            registry.add("spring.datasource.username") { postgres.username }
            registry.add("spring.datasource.password") { postgres.password }
            registry.add("spring.datasource.driver-class-name") { postgres.driverClassName }
            // Let Flyway manage the schema (no auto-ddl from Hibernate)
            registry.add("spring.jpa.hibernate.ddl-auto") { "validate" }
        }
    }
}
