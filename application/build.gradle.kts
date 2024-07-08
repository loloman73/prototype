plugins {
    id("general-conventions")
}


dependencies{
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
    implementation("org.springframework.boot:spring-boot-starter:3.3.1")

}