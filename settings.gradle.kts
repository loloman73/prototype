plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "prototype"
include("web-adapter")
include("persistence-adapter")
include("persistence-adapter")
include("application")
include("configuration")
