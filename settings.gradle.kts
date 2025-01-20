plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
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