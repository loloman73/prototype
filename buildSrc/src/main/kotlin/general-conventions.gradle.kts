plugins {
    kotlin("jvm")
    id("java-library")
}

group = "app.xenagos"
version = "0.0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}