plugins {
    id("general-conventions")
}

dependencies {
    implementation(project(":application"))
    implementation(libs.sb.starter)
    implementation(libs.sb.starter.data.jpa)
    runtimeOnly(libs.postgres)
}