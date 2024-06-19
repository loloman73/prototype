plugins {
    id("general-conventions")
}

dependencies {
    implementation(project(":application"))
    implementation(project(":api"))
    implementation(project(":persistence-adapter"))
    implementation(project(":web-adapter"))
}