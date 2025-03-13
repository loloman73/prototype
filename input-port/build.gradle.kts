plugins {
    id("general-conventions")
}

dependencies {
    implementation(project(":common"))
    implementation(libs.sb.starter.validation)
}