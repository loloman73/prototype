plugins {
    id("general-conventions")
    id("org.springframework.boot") version "3.3.1"
    id("org.jetbrains.kotlin.plugin.spring") version "1.9.24"

}

dependencies {
    implementation(project(":application"))
    implementation(project(":persistence-adapter"))
    implementation(project(":web-adapter"))

    implementation("org.springframework.boot:spring-boot-starter:3.3.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
}