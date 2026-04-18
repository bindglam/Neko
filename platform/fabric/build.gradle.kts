import xyz.jpenilla.resourcefactory.fabric.Environment

plugins {
    id("platform-conventions")
    alias(libs.plugins.resourceFactory.fabric)
    alias(libs.plugins.fabric.loom)
}

val groupString = group.toString()
val versionString = version.toString()
val mcVersionString = property("minecraft_version").toString()

loom {
    splitEnvironmentSourceSets()

    mods {
        create(rootProject.name.lowercase()) {
            sourceSet(sourceSets.main.name)
        }
    }
}

dependencies {
    include(project(":core"))

    minecraft("com.mojang:minecraft:$mcVersionString")
    implementation(libs.fabric.loader)
    implementation(libs.fabric.api)

    implementation(include("net.kyori:adventure-platform-fabric:6.9.0-SNAPSHOT")!!)
    implementation(libs.cloud.fabric)
}

fabricModJson {
    id = rootProject.name.lowercase()
    name = rootProject.name
    description = "Modern Custom Contents Creating Engine"

    entrypoints = listOf(
        mainEntrypoint("$groupString.NekoFabricMod")
    )

    environment = Environment.SERVER

    depends = mapOf(
        "minecraft" to listOf("~${property("minecraft_version")}"),
        "fabricloader" to listOf("*"),

        // fabric-api
        "fabric-api" to listOf("*"),

        // mod libraries
        "adventure-platform-fabric" to listOf("*"),
        "cloud" to listOf("*")
    )

    mixins = listOf(
        mixin("${rootProject.name.lowercase()}.mixins.json")
    )

    authors = listOf(
        person("Bindglam")
    )
    contact {
        sources = "https://github.com/bindglam/Neko/"
        issues = "https://github.com/bindglam/Neko/issues"
        homepage = "https://modrinth.com/plugin/neko"
    }
    icon("assets/icon.png")
    mitLicense()

    version = versionString
}