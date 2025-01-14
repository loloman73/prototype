plugins {
    id("general-conventions")
    alias(libs.plugins.jetbrains.kotlin.noarg)
}

dependencies {
    implementation(project(":application"))
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(libs.sb.starter)
    implementation(libs.sb.starter.data.jpa)
    implementation(libs.flywayPostgresql)

    runtimeOnly(libs.postgres)
}

noArg{
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}