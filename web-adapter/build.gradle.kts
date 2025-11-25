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
    testImplementation(platform(libs.testcontainers.bom))
    testImplementation(libs.testcontainers.junit)
    testImplementation(libs.testcontainers.postgresql)
    testImplementation(libs.assertj)
    // Bring full application context (includes persistence/config) for end-to-end tests
    testImplementation(project(":configuration"))

    testRuntimeOnly(libs.postgres)

}
