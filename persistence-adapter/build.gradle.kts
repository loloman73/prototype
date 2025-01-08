plugins {
    id("general-conventions")
}

dependencies {
    implementation(project(":application"))
    implementation(project(":commun"))
    implementation(libs.sb.starter)
    implementation(libs.sb.starter.data.jpa)
    implementation(libs.flywayPostgresql)
    runtimeOnly(libs.postgres)
}