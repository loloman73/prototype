@file:Suppress("UnstableApiUsage")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
    }
}

rootProject.name = "prototype"
include("configuration")
include("web-adapter")
include("persistence-adapter")
include("input-port")
include("output-port")
include("application")
include("domain")
include("common")