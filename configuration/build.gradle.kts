plugins {
    id("general-conventions")
}

dependencies {
    implementation(project(":application"))
    implementation(project(":persistence-adapter"))
    implementation(project(":web-adapter"))
    implementation("org.springframework.boot:spring-boot:3.3.0")
}