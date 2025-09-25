rootProject.name = "Neko"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

include("api")
include("core")
include("test-plugin")
include("nms:1_21_R5")
include("nms:1_21_R3")
