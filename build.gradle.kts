plugins {
    id("io.spring.dependency-management") version "1.1.5"
 }

subprojects {

    group = "com.example"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    apply{
        plugin("io.spring.dependency-management")
    }

    dependencyManagement{
        imports { mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.0")
        }
    }

}