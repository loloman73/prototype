plugins {
    id("general-conventions")
}

dependencies {
    implementation(project(":application"))
    implementation("org.springframework.boot:spring-boot-starter-web:3.3.1")

    implementation("org.thymeleaf:thymeleaf-spring6:3.1.2.RELEASE")

}
