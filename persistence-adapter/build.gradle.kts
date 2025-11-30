plugins {
    id("general-conventions")
    alias(libs.plugins.jetbrains.kotlin.noarg)
}

dependencies {
    implementation(project(":output-port"))
    implementation(project(":domain"))
    implementation(project(":common"))
    implementation(libs.sb.starter)
    implementation(libs.sb.starter.data.jpa)
    implementation (libs.jetbrains.kotlin.reflect)
    implementation(libs.flywayCore)
    implementation(libs.flywayPostgresql)
    runtimeOnly(libs.postgres)

    testImplementation(libs.sb.starter.test)
    // JUnit 5 Suite API to run grouped tests from IDE
    testImplementation("org.junit.platform:junit-platform-suite-api:1.11.3")
    testRuntimeOnly("org.junit.platform:junit-platform-suite-engine:1.11.3")
    // Testcontainers for integration tests
    testImplementation(platform(libs.testcontainers.bom))
    testImplementation(libs.testcontainers.junit)
    testImplementation(libs.testcontainers.postgresql)
}

noArg{
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}