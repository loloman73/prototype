plugins {
    id("general-conventions")
}

dependencies {
    implementation(project(":application"))
    implementation("org.springframework.boot:spring-boot-starter:3.3.1")
}