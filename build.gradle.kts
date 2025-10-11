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
            pluginJars(project("test-plugin").tasks.jar.flatMap {
                it.archiveFile
            })
            hangar("BetterModel", "1.13.2")
            //modrinth("fastasyncworldedit", "2.13.1")
            //modrinth("worldguard", "7.0.14")
        }
    }

    jar {
        finalizedBy(shadowJar)
    }

    shadowJar {
        archiveClassifier = ""

        dependencies {
            exclude(dependency("org.jetbrains:annotations:13.0")); exclude(dependency("org.jetbrains:annotations:23.0.0")); exclude(dependency("org.jetbrains:annotations:26.0.2"))
        }

        fun prefix(pattern: String) {
            relocate(pattern, "$groupString.shaded.$pattern")
        }
        prefix("kotlin")
    }
}
