plugins {
    id("general-conventions")
}

dependencies {
    implementation(project(":input-port"))
    implementation(project(":common"))

    implementation(libs.sb.starter.web)
    implementation(libs.sb.starter.thymeleaf)
    implementation(libs.thymeleafLD)
    implementation(libs.sb.starter.validation)
    //HTMX
    implementation("org.webjars.npm:htmx.org:2.0.4")
    implementation("org.webjars.npm:htmx-ext-response-targets:2.0.0")
    implementation("io.github.wimdeblauwe:htmx-spring-boot-thymeleaf:4.0.1")

    testImplementation(libs.sb.starter.test)
    // JUnit 5 Suite API to run grouped tests from IDE
    testImplementation(libs.junit.platform.suite.api)
    testRuntimeOnly(libs.junit.platform.suite.engine)
    // Testcontainers for integration tests
    testImplementation(platform(libs.testcontainers.bom))
    testImplementation(libs.testcontainers.junit)
    testImplementation(libs.testcontainers.postgresql)
    testImplementation(libs.assertj)
    // Bring full application context (includes persistence/config) for end-to-end tests
    testImplementation(project(":configuration"))

    testRuntimeOnly(libs.postgres)

}
