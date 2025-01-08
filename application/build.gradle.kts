plugins {
    id("general-conventions")
}

dependencies{
    implementation(project(":input-port"))
    implementation(project(":commun"))
    compileOnly(libs.lombok)
    implementation(libs.sb.starter)
}