plugins {
    id("general-conventions")
}

dependencies {
    implementation(project(":input-port"))
    implementation(project(":common"))

    implementation(libs.sb.starter.web)
    implementation(libs.sb.starter.thymeleaf)
    implementation(libs.thymeleafLD)
    implementation("org.webjars.npm:htmx.org:2.0.4")
    implementation("io.github.wimdeblauwe:htmx-spring-boot-thymeleaf:4.0.1")
}
