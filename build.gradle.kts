plugins {
    id("standard-conventions")
    alias(libs.plugins.runTask.paper)
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(project(":core"))
}

val groupString = group.toString()
val versionString = version.toString()
val mcVersionString = property("minecraft_version").toString()

tasks {
    runServer {
        version(mcVersionString)

        downloadPlugins {
            hangar("BetterModel", "1.11.4")
            modrinth("fastasyncworldedit", "2.13.1")
            modrinth("worldguard", "7.0.14")
        }
    }

    jar {
        finalizedBy(shadowJar)
    }

    shadowJar {
        archiveClassifier = ""

        dependencies {
            exclude(dependency("org.jetbrains:annotations:26.0.2"))
        }

        fun prefix(pattern: String) {
            relocate(pattern, "$groupString.shaded.$pattern")
        }
        prefix("kotlin")
        prefix("dev.jorel.commandapi")
    }
}
