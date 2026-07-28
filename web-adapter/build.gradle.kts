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
    implementation(libs.htmx.core)
    implementation(libs.htmx.ext.response.targets)
    implementation(libs.htmx.spring.boot.thymeleaf)

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
