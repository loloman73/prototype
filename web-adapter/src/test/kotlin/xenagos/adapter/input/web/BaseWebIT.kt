package xenagos.adapter.input.web

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

/**
 * DO NOT USE - IT IS NOT WORKING - CONTAINER STOPS AFTER EACH TEST
**/

// Base class to bootstrap a PostgreSQL Testcontainers instance for end-to-end web tests.
// With @Testcontainers, the container will be started once and stopped once for all tests
@Testcontainers
abstract class BaseWebIT {

    companion object {

        private val image: DockerImageName = DockerImageName.parse("postgres:16-alpine")
        @Container
        @JvmStatic
        val postgres: PostgreSQLContainer<*> = PostgreSQLContainer(image)
            .withDatabaseName("xenagos_test_web")
            .withUsername("test")
            .withPassword("test")

        // Registers the datasource properties for the Spring Boot application context.
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