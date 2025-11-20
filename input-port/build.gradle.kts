plugins {
    id("general-conventions")
}

dependencies {
    implementation(project(":common"))
    api(libs.jakarta.validation)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.assertj)
    testImplementation("org.reflections:reflections:0.10.2")
    testImplementation(kotlin("reflect"))
}