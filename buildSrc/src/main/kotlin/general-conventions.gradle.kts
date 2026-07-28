plugins {
    kotlin("jvm")
    id("java-library")
}

group = "app.xenagos"
version = "0.0.0.1"

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}