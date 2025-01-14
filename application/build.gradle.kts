plugins {
    id("general-conventions")
}

dependencies{
    implementation(project(":input-port"))
    implementation(project(":domain"))
    implementation(project(":common"))
    implementation(libs.sb.starter)
}