plugins {
    id("general-conventions")
}

dependencies {
    implementation(project(":common"))
    api(libs.jakarta.validation)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.assertj)
    testImplementation(libs.reflections)
    testImplementation(libs.jetbrains.kotlin.reflect)
}