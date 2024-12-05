plugins {
    id("general-conventions")
}

dependencies {
    implementation(project(":application"))

    implementation(libs.sb.starter.web)
    implementation(libs.thymeleaf)

}
