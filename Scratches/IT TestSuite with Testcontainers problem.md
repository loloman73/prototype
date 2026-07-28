### Root cause
This happens because your `BaseWebIT` starts and stops a Testcontainers PostgreSQL container per test class, while Spring’s test context is cached and reused across classes. The first class that runs:
- Starts the container in `@BeforeAll`.
- Registers datasource properties via `@DynamicPropertySource` (pointing to that specific container/port).
- After its tests, it stops the container in `@AfterAll`.

When the next test class runs, Spring will typically reuse the already-built `ApplicationContext` (same configuration), so the cached context still contains the old datasource properties pointing to the now-stopped container. Result: subsequent tests fail (often with connection refused / timeout), giving the impression that “only the first test executes correctly.”

File confirming the lifecycle:
- `web-adapter/src/test/kotlin/xenagos/adapter/input/web/BaseWebIT.kt` — it calls `postgres.start()` in `@BeforeAll` and `postgres.stop()` in `@AfterAll`, and registers properties in `@DynamicPropertySource`.

### How to fix
Pick one of the following approaches (in order of preference):

1) Use Testcontainers-managed lifecycle (recommended)
- Convert to the usual pattern with `@Testcontainers` and a static `@Container` field.
- Remove manual `@BeforeAll`/`@AfterAll` start/stop. Let the extension manage a single container per JVM for all tests using the static field.
- With Spring Boot 3.1+, you can also annotate the container with `@ServiceConnection` so you can drop the manual `@DynamicPropertySource`.

Example sketch:
```kotlin
@Testcontainers
abstract class BaseWebIT {
    companion object {
        @Container
        @ServiceConnection // if using Spring Boot 3.1+
        @JvmStatic
        val postgres = PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
    }
}
```
This way the container stays up for the whole test run, and Spring’s cached context stays valid for all classes.

2) Keep manual start, but do not stop between classes
- Remove the `@AfterAll stopContainer()` so the container remains alive for the entire test JVM.
- The container will be torn down automatically at JVM exit. This preserves the datasource properties for all classes sharing the cached context.

3) Force Spring to rebuild the context per class (less efficient)
- Add `@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)` on each test class that extends `BaseWebIT`.
- This ensures `@DynamicPropertySource` runs again and binds to the newly started container for each class, at the cost of slower test execution.

4) One true singleton container
- Create a singleton object (or a JUnit 5 extension) that starts the container once for the entire test run and never stops it until JVM exit; all test classes point their properties to it.

### Extra checks
- Look at the logs for the 2nd test class; you’ll likely see failures connecting to the old container port.
- Ensure your `@IncludeClassNamePatterns(".*ControllerIT")` indeed selects all intended classes (your files like `AdminAccessibilityTagsControllerIT` match, so that part is fine). The issue isn’t selection, it’s the container/context lifecycle.

### TL;DR
Your `@AfterAll` shuts down the DB while Spring reuses the old datasource settings. Use a static `@Container` with `@Testcontainers` (or keep the container alive across classes), or mark classes `@DirtiesContext` to rebuild per class.