plugins {
    id("general-conventions")
    alias(libs.plugins.sb)
    alias(libs.plugins.jetbrains.kotlin.spring)
}

dependencies {
    implementation(project(":application"))
    implementation(project(":persistence-adapter"))
    implementation(project(":web-adapter"))
    implementation(project(":input-port"))
    implementation(project(":domain"))
    implementation(project(":commun"))

    implementation(libs.sb.starter)
    implementation(libs.jetbrains.kotlin.reflect)
}