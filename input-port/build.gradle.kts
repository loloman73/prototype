plugins {
    id("general-conventions")
}

dependencies {
    implementation(project(":common"))
    api(libs.jakarta.validation)
}