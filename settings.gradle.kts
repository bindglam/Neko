pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "Neko"

include("api")
include("core")
include(
    "platform:paper",
    "platform:fabric"
)