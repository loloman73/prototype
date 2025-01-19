plugins {
    id("general-conventions")
}

dependencies {
    implementation(project(":input-port"))
    implementation(project(":common"))

    implementation(libs.sb.starter.web)
    implementation(libs.sb.starter.thymeleaf)
    implementation(libs.thymeleafLD)

}
