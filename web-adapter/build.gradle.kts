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
    implementation("org.webjars.npm:htmx.org:2.0.4")
    implementation("io.github.wimdeblauwe:htmx-spring-boot-thymeleaf:4.0.1")

    testImplementation(libs.sb.starter.test)
    testImplementation(platform(libs.testcontainers.bom))
    testImplementation(libs.testcontainers.junit)
    testImplementation(libs.testcontainers.postgresql)
    testImplementation(libs.assertj)
    testRuntimeOnly(libs.postgres)
    // Bring full application context (includes persistence/config) for end-to-end tests
    testImplementation(project(":configuration"))
}
