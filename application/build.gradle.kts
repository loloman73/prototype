plugins {
    id("general-conventions")
}

dependencies{
    implementation(project(":input-port"))
    implementation(project(":domain"))
    implementation(project(":commun"))
    compileOnly(libs.lombok)
    implementation(libs.sb.starter)
}