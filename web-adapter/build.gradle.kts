plugins {
    id("general-conventions")
}

dependencies {
    implementation(project(":application"))
    implementation("org.springframework.boot:spring-boot-starter-web:3.3.1")
}
