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
    implementation(libs.flywayPostgresql)

    runtimeOnly(libs.postgres)

    testImplementation(libs.sb.starter.test)
}

noArg{
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}