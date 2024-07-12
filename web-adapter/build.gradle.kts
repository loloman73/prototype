plugins {
    id("general-conventions")
}

dependencies {
    implementation(project(":application"))
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("org.springframework.boot:spring-boot-starter-web:3.3.1")
}
