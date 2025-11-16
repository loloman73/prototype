plugins {
    id("general-conventions")
    alias(libs.plugins.jetbrains.kotlin.spring)
}

dependencies{
    implementation(project(":input-port"))
    implementation(project(":output-port"))
    implementation(project(":domain"))
    implementation(project(":common"))
    implementation(libs.spring.context)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.assertj)
}