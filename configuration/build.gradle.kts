plugins {
    id("general-conventions")
}

dependencies {
    implementation(project(":application"))
    implementation(project(":persistence-adapter"))
    implementation(project(":web-adapter"))
}