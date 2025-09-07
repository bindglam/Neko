rootProject.name = "Neko"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

include("api")
include("core")
include("nms:1_21_R5")
